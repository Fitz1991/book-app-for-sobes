package com.infoshell.presentation.ui.catalog.category_products

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.infoshell.book24.R
import com.infoshell.book24.databinding.FragmentCategoryProductsBinding
import com.infoshell.domain.entity.ProductFilter
import com.infoshell.presentation.di.PRODUCT_ADAPTER
import com.infoshell.presentation.enity.CategoryProductsQueryParams
import com.infoshell.presentation.enity.DisplayCategory
import com.infoshell.presentation.enity.DisplayProduct
import com.infoshell.presentation.enity.RequestStatus
import com.infoshell.presentation.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named


class CategoryProductsFragment : BaseFragment<FragmentCategoryProductsBinding>(), CategoryProductsAdapter.OnItemClickListener {
    override var layoutId: Int = R.layout.fragment_category_products
    private val viewModel: CategoryProductsViewModel by viewModel { parametersOf(queryParams, productRequestStatus) }

    private val PAGE: Int = 1
    private val PAGE_SIZE: Int = 100

    private val queryParams = CategoryProductsQueryParams()
    private var productRequestStatus = RequestStatus()

    private val productAdapter: CategoryProductsAdapter by inject(named(PRODUCT_ADAPTER))

    override fun handle(view: View, displayProduct: DisplayProduct, actionType: CategoryProductsAdapter.ActionType) {
        when(actionType) {
            CategoryProductsAdapter.ActionType.ITEM_CLICK -> {
                val product = bundleOf("product" to displayProduct)
                view.findNavController().navigate(R.id.navigate_to_product, product)
            }
            CategoryProductsAdapter.ActionType.ADD_TO_BASKET -> {
                viewModel.addProductToBasket(displayProduct)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val category = arguments?.getParcelable<DisplayCategory>("category")
        queryParams.filter = ProductFilter(sectionId = category?.id).toString()
        queryParams.page = PAGE
        queryParams.pageSize = PAGE_SIZE
        viewModel.categoryName.set(category?.name)
        init()
    }

    private fun init() {
        viewBinding?.viewModel = viewModel
        viewBinding?.productRequestStatus = productRequestStatus
        viewBinding?.productCategoryRV?.layoutManager = GridLayoutManager(this.context, 2)
        productAdapter.onClickHandler = this
        viewBinding?.productCategoryRV?.adapter = productAdapter
        viewModel.listLiveData?.observe(this, Observer {
            productAdapter.submitList(it)
        })
    }
}
