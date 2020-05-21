package com.infoshell.presentation.ui.custom.viewpager

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.infoshell.book24.R
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

private const val DEFAULT_INTERVAL = 10 * 1000

class LoopingViewPager : ViewPager {

    protected var isInfinite = true
    protected var isScaleSideItems = false
    protected var isAutoScroll = false
    protected var wrapContent = true
    protected var aspectRatio = 0f
    protected var itemAspectRatio = 0f

    //AutoScroll
    private var interval = DEFAULT_INTERVAL
    private var previousPosition = 0
    private var currentPagePosition = 0
    private var isAutoScrollResumed = false
    private val autoScrollHandler = Handler()
    private val autoScrollRunnable = Runnable {
        if (adapter == null || !isAutoScroll || adapter!!.count < 2) return@Runnable
        if (!isInfinite && adapter!!.count - 1 == currentPagePosition) {
            currentPagePosition = 0
        } else {
            currentPagePosition++
        }
        setCurrentItem(currentPagePosition, true)
    }

    //For Indicator
    private var indicatorPageChangeListener: IndicatorPageChangeListener? = null
    private var previousScrollState = SCROLL_STATE_IDLE
    private var scrollState = SCROLL_STATE_IDLE
    private var isToTheRight = true
    /**
     * This boolean indicates whether LoopingViewPager needs to continuously tell the indicator about
     * the progress of the scroll, even after onIndicatorPageChange().
     * If indicator is smart, it should be able to finish the animation by itself after we told it that a position has been selected.
     * If indicator is not smart, then LoopingViewPager will continue to fire onIndicatorProgress() to update the indicator
     * transition position.
     */
    private var isIndicatorSmart = false

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        val a =
            context.theme.obtainStyledAttributes(attrs, R.styleable.LoopingViewPager, 0, 0)
        try {
            isInfinite = a.getBoolean(R.styleable.LoopingViewPager_isInfinite, false)
            isScaleSideItems = a.getBoolean(R.styleable.LoopingViewPager_isScaleSideItems, false)
            isAutoScroll = a.getBoolean(R.styleable.LoopingViewPager_autoScroll, false)
            wrapContent = a.getBoolean(R.styleable.LoopingViewPager_wrap_content, true)
            interval = a.getInt(R.styleable.LoopingViewPager_scrollInterval, DEFAULT_INTERVAL)
            aspectRatio = a.getFloat(R.styleable.LoopingViewPager_viewpagerAspectRatio, 0f)
            itemAspectRatio = a.getFloat(R.styleable.LoopingViewPager_itemAspectRatio, 0f)
            isAutoScrollResumed = isAutoScroll
        } finally {
            a.recycle()
        }
        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        val width = MeasureSpec.getSize(widthMeasureSpec)
        if (aspectRatio > 0) {
            val height =
                (MeasureSpec.getSize(widthMeasureSpec).toFloat() / aspectRatio).roundToInt()
            val finalWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
            val finalHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
            // If child items can scale, fit inside their parent by increasing left/right padding.
            if (itemAspectRatio > 0 && itemAspectRatio != aspectRatio) {
                // super has to be called in the beginning so the child views can be initialized.
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                // Remove padding from width
                var childWidthSize = width - paddingLeft - paddingRight
                // Make child width MeasureSpec
                var childWidthMeasureSpec =
                    MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY)
                var i = 0
                while (i < childCount) {
                    val child = getChildAt(i)
                    child.measure(
                        childWidthMeasureSpec,
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                    )
                    val w = child.measuredWidth
                    val h = child.measuredHeight
                    if (h > 0 && h > height) {
                        val ratio = w.toFloat() / h
                        // Round down largest width that fits.
                        val optimalWidth = floor(height * ratio.toDouble())
                        // Round up new padding size.
                        val newPadding = ((width - optimalWidth) / 2).roundToInt()
                        // Set new padding values
                        setPadding(newPadding, paddingTop, newPadding, paddingBottom)
                        // Remove padding from width
                        childWidthSize = width - paddingLeft - paddingRight
                        // Make child width MeasureSpec
                        childWidthMeasureSpec =
                            MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY)
                        child.measure(
                            childWidthMeasureSpec,
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                        )
                    } else i++
                }
            }
            super.onMeasure(finalWidthMeasureSpec, finalHeightMeasureSpec)
        } else {
            if (wrapContent) {
                val mode = MeasureSpec.getMode(heightMeasureSpec)
                // Unspecified means that the ViewPager is in a ScrollView WRAP_CONTENT.
                // At Most means that the ViewPager is not in a ScrollView WRAP_CONTENT.
                if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
                    // super has to be called in the beginning so the child views can be initialized.
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                    var height = 0
                    // Remove padding from width
                    val childWidthSize = width - paddingLeft - paddingRight
                    // Make child width MeasureSpec
                    val childWidthMeasureSpec =
                        MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY)
                    for (i in 0 until childCount) {
                        val child = getChildAt(i)
                        child.measure(
                            childWidthMeasureSpec,
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                        )
                        val h = child.measuredHeight
                        if (h > height) {
                            height = h
                        }
                    }
                    // Add padding back to child height
                    height += paddingTop + paddingBottom
                    heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
                }
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun setAdapter(adapter: PagerAdapter?) {
        super.setAdapter(adapter)
        if (isInfinite) setCurrentItem(1, false)
    }

    fun resumeAutoScroll() {
        isAutoScrollResumed = true
        autoScrollHandler.postDelayed(autoScrollRunnable, interval.toLong())
    }

    fun pauseAutoScroll() {
        isAutoScrollResumed = false
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
    }//Dummy first item is selected. Indicator should be at the first one//Dummy last item is selected. Indicator should be at the last one

    /**
     * A method that helps you integrate a ViewPager Indicator.
     * This method returns the expected position (Starting from 0) of indicators.
     * This method should be used after currentPagePosition is updated.
     */
    val indicatorPosition: Int
        get() = if (!isInfinite) currentPagePosition
        else {
            if (adapter !is LoopingPagerAdapter<*>) currentPagePosition
            when (currentPagePosition) {
                0 -> (adapter as LoopingPagerAdapter<*>?)!!.listCount - 1 //Dummy last item is selected. Indicator should be at the last one
                (adapter as LoopingPagerAdapter<*>?)!!.lastItemPosition + 1 -> 0 //Dummy first item is selected. Indicator should be at the first one
                else -> currentPagePosition - 1
            }
        }

    /**
     * A method that helps you integrate a ViewPager Indicator.
     * This method returns the expected position (Starting from 0) of indicators.
     * This method should be used before currentPagePosition is updated, when user is trying to
     * select a different page, i.e. onPageScrolled() is triggered.
     */
    fun getSelectingIndicatorPosition(isToTheRight: Boolean): Int {
        if (scrollState == SCROLL_STATE_SETTLING
            || scrollState == SCROLL_STATE_IDLE
            || previousScrollState == SCROLL_STATE_SETTLING
            && scrollState == SCROLL_STATE_DRAGGING
        ) return indicatorPosition

        val delta = if (isToTheRight) 1 else -1
        return if (isInfinite) {
            if (adapter !is LoopingPagerAdapter<*>) return currentPagePosition + delta
            if (currentPagePosition == 1 && !isToTheRight) {
                //Special case for first page to last page
                (adapter as LoopingPagerAdapter<*>?)!!.lastItemPosition - 1
            } else if (currentPagePosition == (adapter as LoopingPagerAdapter<*>?)!!.lastItemPosition && isToTheRight) 0 //Special case for last page to first page
            else currentPagePosition + delta - 1
        } else currentPagePosition + delta
    }

    /**
     * A method that helps you integrate a ViewPager Indicator.
     * This method returns the expected count of indicators.
     */
    val indicatorCount: Int
        get() = if (adapter is LoopingPagerAdapter<*>) (adapter as LoopingPagerAdapter<*>?)!!.listCount
        else adapter!!.count

    /**
     * This function needs to be called if dataSet has changed,
     * in order to reset current selected item and currentPagePosition and autoPageSelectionLock.
     */
    fun reset() {
        currentPagePosition = if (isInfinite) {
            setCurrentItem(1, false)
            1
        } else {
            setCurrentItem(0, false)
            0
        }
    }

    fun setIndicatorSmart(isIndicatorSmart: Boolean) {
        this.isIndicatorSmart = isIndicatorSmart
    }

    fun setIndicatorPageChangeListener(callback: IndicatorPageChangeListener?) {
        indicatorPageChangeListener = callback
    }

    fun setInterval(interval: Int) {
        this.interval = interval
        resetAutoScroll()
    }

    private fun init() {
        addOnPageChangeListener(object : OnPageChangeListener {
            var currentPosition = 0f
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (indicatorPageChangeListener == null) return
                isToTheRight = position + positionOffset >= currentPosition
                if (positionOffset == 0f) currentPosition = position.toFloat()
                val realPosition = getSelectingIndicatorPosition(isToTheRight)
                val progress: Float
                progress = if (scrollState == SCROLL_STATE_SETTLING
                    && abs(currentPagePosition - previousPosition) > 1
                ) {
                    val pageDiff = abs(currentPagePosition - previousPosition)
                    if (isToTheRight) {
                        (position - previousPosition).toFloat() / pageDiff + positionOffset / pageDiff
                    } else {
                        (previousPosition - (position + 1)).toFloat() / pageDiff + (1 - positionOffset) / pageDiff
                    }
                } else {
                    if (isToTheRight) positionOffset else 1 - positionOffset
                }
                if (progress == 0f || progress > 1) return
                if (isIndicatorSmart) {
                    if (scrollState != SCROLL_STATE_DRAGGING) return
                    indicatorPageChangeListener!!.onIndicatorProgress(realPosition, progress)
                } else {
                    if (scrollState == SCROLL_STATE_DRAGGING) {
                        if (isToTheRight && abs(realPosition - currentPagePosition) == 2 ||
                            !isToTheRight && realPosition == currentPagePosition
                        ) {
                            //If this happens, it means user is fast scrolling where onPageSelected() is not fast enough
                            //to catch up with the scroll, thus produce wrong position value.
                            return
                        }
                    }
                    indicatorPageChangeListener!!.onIndicatorProgress(realPosition, progress)
                }
            }

            override fun onPageSelected(position: Int) {
                previousPosition = currentPagePosition
                currentPagePosition = position
                indicatorPageChangeListener?.onIndicatorPageChange(indicatorPosition)
                if (isAutoScrollResumed) {
                    autoScrollHandler.removeCallbacks(autoScrollRunnable)
                    autoScrollHandler.postDelayed(autoScrollRunnable, interval.toLong())
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (!isIndicatorSmart) {
                    if (scrollState == SCROLL_STATE_SETTLING && state == SCROLL_STATE_DRAGGING) {
                        indicatorPageChangeListener?.onIndicatorProgress(
                            getSelectingIndicatorPosition(isToTheRight), 1f
                        )
                    }
                }
                previousScrollState = scrollState
                scrollState = state
                if (state == SCROLL_STATE_IDLE) {
                    //Below are code to achieve infinite scroll.
                    //We silently and immediately flip the item to the first / last.
                    if (isInfinite) {
                        if (adapter == null) return
                        val itemCount = adapter!!.count
                        if (itemCount < 2) return
                        val index = currentItem
                        if (index == 0) setCurrentItem(itemCount - 2, false) //Real last item
                        else if (index == itemCount - 1) setCurrentItem(1, false) //Real first item
                    }
                    indicatorPageChangeListener?.onIndicatorProgress(indicatorPosition, 1f)
                }
            }
        })
        if (isInfinite) setCurrentItem(1, false)
        if (isScaleSideItems) setPageTransformer(false, ScalePageTransformer())
    }

    private fun resetAutoScroll() {
        pauseAutoScroll()
        resumeAutoScroll()
    }

    interface IndicatorPageChangeListener {
        fun onIndicatorProgress(selectingPosition: Int, progress: Float)
        fun onIndicatorPageChange(newIndicatorPosition: Int)
    }

}