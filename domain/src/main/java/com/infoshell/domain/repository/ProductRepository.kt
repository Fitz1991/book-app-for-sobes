package com.infoshell.domain.repository

import com.infoshell.domain.entity.BaseDataWithMeta
import com.infoshell.domain.entity.Product
import io.reactivex.Single

interface ProductRepository {

    fun getProducts(page: Int = 1, pageSize: Int = 10, filter: String = ""): Single<BaseDataWithMeta<Product>>
    fun getProduct(id: Product): Single<Product>
}