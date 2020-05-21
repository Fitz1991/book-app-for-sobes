package com.infoshell.data.mapper.domain

import com.infoshell.data.entity.ApiCategory
import com.infoshell.domain.entity.Category

class CategoryDomainMapper : ApiToDomainMapper<ApiCategory, Category> {

    override fun mapToDomain(domainModel: ApiCategory): Category {
        return Category(
            name = domainModel.name,
            icon = domainModel.icon ?: "",
            id = domainModel.id
        )
    }

}