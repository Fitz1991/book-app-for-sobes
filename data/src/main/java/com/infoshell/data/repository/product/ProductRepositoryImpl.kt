package com.infoshell.data.repository.product

import com.infoshell.domain.entity.BaseDataWithMeta
import com.infoshell.domain.entity.Product
import com.infoshell.domain.repository.ProductRepository
import io.reactivex.Single

class ProductRepositoryImpl(private val remoteSource: ProductRemoteSource) : ProductRepository {

    override fun getProducts(page: Int, pageSize: Int, filter: String): Single<BaseDataWithMeta<Product>> {
        return remoteSource.getProducts(page, pageSize, filter)
    }

    override fun getProduct(product: Product): Single<Product> {
        return remoteSource.getAllInfoProduct(product)
    }
}