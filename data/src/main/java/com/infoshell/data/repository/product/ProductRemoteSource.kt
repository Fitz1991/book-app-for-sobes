package com.infoshell.data.repository.product

import com.infoshell.domain.entity.BaseDataWithMeta
import com.infoshell.domain.entity.Product
import io.reactivex.Single

interface ProductRemoteSource {
    fun getProducts(page: Int = 1, pageSize : Int = 10, filter: String = ""): Single<BaseDataWithMeta<Product>>
    fun getAllInfoProduct(product: Product): Single<Product>
}