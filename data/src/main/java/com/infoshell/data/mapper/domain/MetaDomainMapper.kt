package com.infoshell.data.mapper.domain

import com.infoshell.data.response.data.MetaData

class MetaDomainMapper : ApiToDomainMapper<MetaData, com.infoshell.domain.entity.MetaData> {
    override fun mapToDomain(domainModel: MetaData): com.infoshell.domain.entity.MetaData {
        return com.infoshell.domain.entity.MetaData(
            size = domainModel.size,
            total = domainModel.total,
            page = domainModel.page
        )
    }
}