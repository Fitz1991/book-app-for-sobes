package com.infoshell.presentation.ui.catalog.category_products

import androidx.databinding.ObservableField
import androidx.paging.PageKeyedDataSource
import com.infoshell.data.ext.inBackground
import com.infoshell.domain.usecase.ProductUseCase
import com.infoshell.presentation.enity.CategoryProductsQueryParams
import com.infoshell.presentation.enity.DisplayProduct
import com.infoshell.presentation.enity.RequestStatus
import com.infoshell.presentation.mapper.display.ProductDisplayMapper
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import kotlin.math.ceil


class CategoryProductsDataSource(
    private val productUseCase: ProductUseCase,
    private val productDisplayMapper: ProductDisplayMapper
) : PageKeyedDataSource<Int, DisplayProduct>() {
    lateinit var queryParams: CategoryProductsQueryParams
    lateinit var productRequestStatus: RequestStatus
    private var productData = listOf<DisplayProduct>()
    var totalCount = ObservableField<String>()

    val compositeDisposable = CompositeDisposable()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, DisplayProduct>
    ) {
        compositeDisposable.add(
            productUseCase.getProductsFromCategory(
                queryParams.page,
                params.requestedLoadSize,
                queryParams.filter
            )
                .doOnSubscribe { productRequestStatus.isProcessing.set(true) }
                .doFinally { productRequestStatus.isProcessing.set(false) }
                .inBackground()
                .subscribe({ result ->
                    productRequestStatus.isLoaded.set(true)
                    if (totalCount.get().isNullOrEmpty()) totalCount.set(result.meta.total.toString())
                    productData = result.items.map {
                        productDisplayMapper.mapToDisplay(it)
                    }
                    queryParams.page++
                    callback.onResult(productData, null, queryParams.page)

                }, {
                    Timber.d(it, it.message)
                })
        )

    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, DisplayProduct>
    ) {
        compositeDisposable.add(
            productUseCase.getProductsFromCategory(
                params.key.toInt(),
                params.requestedLoadSize,
                queryParams.filter
            )
                .inBackground()
                .doOnSubscribe { productRequestStatus.isProcessing.set(true) }
                .doFinally { productRequestStatus.isProcessing.set(false) }
                .subscribe({ result ->
                    productRequestStatus.isLoaded.set(true)
                    productData = result.items.map {
                        productDisplayMapper.mapToDisplay(it)
                    }
                    queryParams.page++
                    val nextPage =
                        if (params.key == ceil((result.meta.total).toDouble() / params.requestedLoadSize).toInt()) null else
                            params.key.toInt() + 1

                    callback.onResult(productData, nextPage)

                }, {
                    Timber.d(it, it.message)
                })
        )


    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, DisplayProduct>
    ) {
    }
}