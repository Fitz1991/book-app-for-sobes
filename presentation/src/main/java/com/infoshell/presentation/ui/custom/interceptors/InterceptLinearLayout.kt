package com.infoshell.presentation.ui.custom.interceptors

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.infoshell.book24.R

class InterceptLinearLayout : LinearLayout {
    private var isIntercept = false

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr) {
        initAttrs(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        if (!this.isInEditMode) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.InterceptLinearLayout)
            isIntercept = ta.getBoolean(R.styleable.InterceptLinearLayout_isIntercept, false)
            ta.recycle()
        }
    }

    fun setIntercept(intercept: Boolean) {
        isIntercept = intercept
    }

    override fun onInterceptTouchEvent(ev: MotionEvent) = isIntercept
}