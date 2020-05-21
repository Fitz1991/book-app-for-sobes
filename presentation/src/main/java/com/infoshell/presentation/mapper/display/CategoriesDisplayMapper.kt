package com.infoshell.presentation.mapper.display

import com.infoshell.domain.entity.Category
import com.infoshell.presentation.enity.DisplayCategory

class CategoriesDisplayMapper : DomainToDisplayMapper<Category, DisplayCategory> {

    override fun mapToDisplay(domainModel: Category): DisplayCategory {
        return DisplayCategory(
            id = domainModel.id,
            icon = domainModel.icon,
            name = domainModel.name
        )
    }
}