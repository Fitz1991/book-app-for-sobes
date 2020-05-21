package com.infoshell.data.repository.category

import com.infoshell.domain.entity.Category
import io.reactivex.Single

interface CategoryRemoteSource {

    fun getAllCategories(): Single<List<Category>>
}