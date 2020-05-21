package com.infoshell.presentation.ui.catalog.product


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.infoshell.book24.R
import com.infoshell.book24.databinding.FragmentProductBinding
import com.infoshell.domain.entity.Review
import com.infoshell.presentation.di.PRODUCT_COVER_ADAPTER
import com.infoshell.presentation.di.PRODUCT_RECOMMENDATIONS_ADAPTER
import com.infoshell.presentation.di.PRODUCT_REVIEWS_ADAPTER
import com.infoshell.presentation.enity.DisplayProduct
import com.infoshell.presentation.ui.base.BaseFragment
import com.infoshell.presentation.ui.home.ProductsAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named


/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : BaseFragment<FragmentProductBinding>(), MoreClickHandler {

    override var layoutId = R.layout.fragment_product
    private val viewModel: ProductViewModel by viewModel()
    private val productCoverAdapter: ProductCoverAdapter by inject(named(PRODUCT_COVER_ADAPTER))
    private val productReviewsAdapter: ProductReviewsAdapter by inject(named(PRODUCT_REVIEWS_ADAPTER))
    private val recommendAdapter: ProductsAdapter by inject(named(PRODUCT_RECOMMENDATIONS_ADAPTER))
    private lateinit var chankReviews: List<List<Review>>
    private lateinit var reviews: MutableList<Review>

    companion object {
        const val MAX_LINES_DETAIL_TEXT = 2
        const val PAGE_SIZE_REVIEWS = 2
        const val NUMBER_OF_COVERS = 2
    }

    private var isPressedMoreTapDetailText = false
    private var startPageReviews = 0
    private var isLastPageReviews = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val product = arguments?.getParcelable<DisplayProduct>("product")
        init(product)
    }

    private fun init(product: DisplayProduct?) {
        viewBinding?.viewModel = viewModel
        if (product != null) viewModel.getProduct(product)
        viewBinding?.productCoverVP?.adapter = productCoverAdapter
        viewBinding?.coverIndicatorTL?.setupWithViewPager(viewBinding?.productCoverVP, true)
        viewBinding?.handler = this
        viewBinding?.reviewsRV?.adapter = productReviewsAdapter
        viewBinding?.recommendationsListRV?.adapter = recommendAdapter

        viewModel.product.observe(viewLifecycleOwner, Observer {
            viewBinding?.product = it

            if (it.images.isNotEmpty()) {
                if (it.images.size > 1) productCoverAdapter.refreshList(
                    it.images.subList(
                        0,
                        NUMBER_OF_COVERS
                    )
                )
                else {
                    productCoverAdapter.refreshList(it.images)
                    viewBinding?.coverIndicatorTL?.visibility = View.GONE
                }
            }
            viewBinding?.productCoverVP?.reset()
            viewBinding?.starRating?.rating = 3f
            if (it.reviews.isNotEmpty()) {
                chankReviews = it?.reviews?.chunked(PAGE_SIZE_REVIEWS) ?: listOf()
                if (chankReviews.size == 1) {
                    viewBinding?.reviewsMoreTV?.visibility = View.GONE
                }
                reviews = chankReviews.get(startPageReviews) as MutableList<Review>
                productReviewsAdapter.setReviewsList(reviews)
            }
        })

        viewModel.recommendations.observe(viewLifecycleOwner, Observer {
            recommendAdapter.setProductsList(it)
        })
    }


    override fun handleDatailText(view: View) {
        if (!isPressedMoreTapDetailText) {
            viewBinding?.detalText?.maxLines = Integer.MAX_VALUE
            isPressedMoreTapDetailText = true
        } else {
            viewBinding?.detalText?.maxLines = MAX_LINES_DETAIL_TEXT
            isPressedMoreTapDetailText = false
        }
    }

    override fun handleReviewsMore(view: View) {
        if (!isLastPageReviews) {
            var last = startPageReviews + 1
            if (chankReviews.size != last) {
                startPageReviews++
                reviews.addAll(chankReviews.get(startPageReviews))
                productReviewsAdapter.setReviewsList(reviews)
                last++
            }
            if (chankReviews.size == last) {
                isLastPageReviews = true
                viewBinding?.reviewsMoreTV?.visibility = View.GONE
            }
        }
    }
}
