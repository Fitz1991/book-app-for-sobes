package com.infoshell.domain.repository

import com.infoshell.domain.entity.Category
import com.infoshell.domain.entity.Result
import io.reactivex.Single

interface CategoryRepository {

    fun getAllCategories(): Single<Result<List<Category>>>
}