package com.infoshell.domain.usecase

import com.infoshell.domain.entity.BaseDataWithMeta
import com.infoshell.domain.entity.Basket
import com.infoshell.domain.entity.Product
import com.infoshell.domain.repository.BasketRepository
import com.infoshell.domain.repository.ProductRepository
import io.reactivex.Single

class ProductUseCase(
    private val productRepository: ProductRepository,
    private val basketRepository: BasketRepository
) {
    fun getProductsFromCategory(page: Int, pageSize: Int, filter: String): Single<BaseDataWithMeta<Product>> {
        return productRepository.getProducts(page, pageSize, filter)
    }

    fun getProduct(product: Product): Single<Product> {
        return productRepository.getProduct(product)
    }

    fun getRecommendationsProduct(page: Int = 1): Single<BaseDataWithMeta<Product>> {
        return productRepository.getProducts()
    }

    fun addProductToBasket(product: Product): Single<Basket> {
        return basketRepository.putItemToBasket(product)
    }
}