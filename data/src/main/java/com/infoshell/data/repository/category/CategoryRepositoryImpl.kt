package com.infoshell.data.repository.category

import com.infoshell.data.network.ErrorHandlerImpl
import com.infoshell.domain.entity.Category
import com.infoshell.domain.entity.Result
import com.infoshell.domain.repository.CategoryRepository

class CategoryRepositoryImpl(private val remoteSource: CategoryRemoteSource) : CategoryRepository {

    override fun getAllCategories() = remoteSource
        .getAllCategories()
        .map {
            Result.Success(it) as Result<List<Category>>
        }
        .onErrorReturn { throwable ->
            Result.Error(ErrorHandlerImpl().getError(throwable))
        }
}