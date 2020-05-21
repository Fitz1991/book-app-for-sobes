package com.infoshell.presentation.ui.catalog.category

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.infoshell.data.ext.inBackground
import com.infoshell.domain.entity.Category
import com.infoshell.domain.entity.ErrorEntity
import com.infoshell.domain.usecase.CategoryUseCase
import com.infoshell.presentation.enity.DisplayCategory
import com.infoshell.presentation.ext.default
import com.infoshell.presentation.mapper.display.DomainToDisplayMapper
import com.infoshell.presentation.ui.base.BaseViewModel
import com.infoshell.domain.entity.Result
import timber.log.Timber


class CategoryViewModel(
    private val categoryUseCase: CategoryUseCase,
    private val categoryDisplayMapper: DomainToDisplayMapper<Category, DisplayCategory>,
    application: Application
) : BaseViewModel(application) {

    val isCategoryProcessing = ObservableBoolean()
    val categoryData = MutableLiveData<List<DisplayCategory>?>()

    fun getAllCategories() {
        compositeDisposable.add(
            categoryUseCase.getAllCategories()
                .doOnSubscribe { isCategoryProcessing.set(true) }
                .inBackground()
                .doFinally { isCategoryProcessing.set(false) }
                .subscribe { result ->
                    when (result) {
                        is Result.Success -> {
                            categoryData.value = result.data.map {
                                categoryDisplayMapper.mapToDisplay(it)
                            }
                        }
                        is Result.Error -> {
                            when (result.error) {
                                is ErrorEntity.Network -> Timber.d("Ошибка в с сетью")
                                is ErrorEntity.Api -> Timber.d("Неправильный апи адрес")
                                is ErrorEntity.ServiceUnavailable -> Timber.d("Сервис не доступен")
                                else -> Timber.d("Неизвестная ошибка")
                            }
                        }
                    }
                })
    }
}



