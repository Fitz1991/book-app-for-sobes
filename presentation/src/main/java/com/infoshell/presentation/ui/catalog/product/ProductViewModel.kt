package com.infoshell.presentation.ui.catalog.product


import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.infoshell.data.ext.inBackground
import com.infoshell.domain.entity.Product
import com.infoshell.domain.usecase.ProductUseCase
import com.infoshell.presentation.enity.DisplayProduct
import com.infoshell.presentation.mapper.display.DomainToDisplayMapper
import com.infoshell.presentation.mapper.domain.DisplayToDomainMapper
import com.infoshell.presentation.ui.base.BaseViewModel
import timber.log.Timber

class ProductViewModel(
    private val productUseCase: ProductUseCase,
    private val productDisplayMapper: DomainToDisplayMapper<Product, DisplayProduct>,
    private val productDomainMapper: DisplayToDomainMapper<DisplayProduct, Product>,
    application: Application
) : BaseViewModel(application) {


    var product = MutableLiveData<DisplayProduct>()
    val recommendations: MutableLiveData<List<DisplayProduct>> = MutableLiveData()
    val isRecommendationsLoaded = ObservableBoolean(false)
    val isRecommendationsProcessing = ObservableBoolean()

    init {
        getRecommendationsProduct()
    }
    fun getProduct(displayProduct: DisplayProduct) {
        if (displayProduct.isbn.isNotEmpty()) {
            compositeDisposable.add(

                productUseCase.getProduct(productDomainMapper.mapToApi(displayProduct))
                    .inBackground()
                    .subscribe({
                        val displayProduct = productDisplayMapper.mapToDisplay(it)
                        product.value = displayProduct
                    }, {
                        Timber.d(it.message)
                    })
            )
        } else product.value = displayProduct
    }

    fun getRecommendationsProduct() {
        compositeDisposable.add(
            productUseCase.getRecommendationsProduct()
                .doOnSubscribe { isRecommendationsProcessing.set(true) }
                .inBackground()
                .doFinally { isRecommendationsProcessing.set(false) }
                .map { it.items.map { productDisplayMapper.mapToDisplay(it) } }
                .subscribe({
                    isRecommendationsLoaded.set(true)
                    recommendations.value = it
                }, { onError(it) })
        )
    }
}