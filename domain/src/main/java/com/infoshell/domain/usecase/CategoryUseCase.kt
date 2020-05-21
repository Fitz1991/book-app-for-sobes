package com.infoshell.domain.usecase

import com.infoshell.domain.entity.Category
import com.infoshell.domain.entity.Result
import com.infoshell.domain.repository.CategoryRepository
import io.reactivex.Single

class CategoryUseCase(private val categoryRepository: CategoryRepository) {
    fun getAllCategories(): Single<Result<List<Category>>> {
        return categoryRepository.getAllCategories()
    }
}