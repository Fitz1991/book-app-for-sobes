package com.infoshell.presentation.ui.catalog.category_products

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.infoshell.data.ext.inBackground
import com.infoshell.presentation.enity.DisplayProduct
import com.infoshell.presentation.ui.base.BaseViewModel
import timber.log.Timber


class CategoryProductsViewModel(
    private val productDataSourceFactory: CategoryProductsDataSourceFactory,
    application: Application
) : BaseViewModel(application) {

    init {
        initializePaging()
    }

    var categoryName = ObservableField<String>()
    var countProduct = ObservableField<String>()
    var listLiveData: LiveData<PagedList<DisplayProduct>>? = null

    fun addProductToBasket(product: DisplayProduct) {
        compositeDisposable.add(
            productDataSourceFactory.addProductToBasket(product)
                .inBackground()
                .subscribe({

                }, {
                    onError(it)
                })
        )
    }

    private fun initializePaging() {
        compositeDisposable.add(
            productDataSourceFactory.productDataSource.subscribe {
                countProduct = it.totalCount
                compositeDisposable.addAll(it.compositeDisposable)
            }
        )

        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(100)
            .setInitialLoadSizeHint(100)
            .setPrefetchDistance(50)
            .build()

        listLiveData = LivePagedListBuilder(productDataSourceFactory, pagedListConfig)
            .build()
    }
}