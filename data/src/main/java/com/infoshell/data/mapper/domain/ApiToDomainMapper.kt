package com.infoshell.data.mapper.domain

interface ApiToDomainMapper<in ApiModel, out DomainModel> {

    fun mapToDomain(domainModel: ApiModel): DomainModel
}