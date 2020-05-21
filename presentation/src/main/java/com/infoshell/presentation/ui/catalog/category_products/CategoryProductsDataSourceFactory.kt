package com.infoshell.presentation.ui.catalog.category_products

import androidx.paging.DataSource
import com.infoshell.domain.entity.Basket
import com.infoshell.domain.entity.Product
import com.infoshell.domain.usecase.ProductUseCase
import com.infoshell.presentation.enity.DisplayProduct
import com.infoshell.presentation.mapper.display.ProductDisplayMapper
import com.infoshell.presentation.mapper.domain.DisplayToDomainMapper
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.koin.core.parameter.DefinitionParameters
import org.koin.java.KoinJavaComponent.inject


class CategoryProductsDataSourceFactory(
    var params: DefinitionParameters,
    var productDisplayMapper: ProductDisplayMapper,
    var productDomainMapper: DisplayToDomainMapper<DisplayProduct, Product>
) :
    DataSource.Factory<Int, DisplayProduct>() {

    private val productUseCase: ProductUseCase by inject(ProductUseCase::class.java)
    var productDataSource: PublishSubject<CategoryProductsDataSource> = PublishSubject.create()

    override fun create(): DataSource<Int, DisplayProduct> {
        val productDS = CategoryProductsDataSource(productUseCase, productDisplayMapper).apply {
            queryParams = params[0]; productRequestStatus = params[1]
        }
        productDataSource.onNext(productDS)
        return productDS
    }

    fun addProductToBasket(product: DisplayProduct): Single<Basket> {
        return productUseCase.addProductToBasket(productDomainMapper.mapToApi(product))
    }
}
