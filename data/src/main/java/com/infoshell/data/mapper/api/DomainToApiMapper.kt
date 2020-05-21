package com.infoshell.data.mapper.api

interface DomainToApiMapper<in DomainModel, out ApiModel> {

    fun mapToApi(domainModel: DomainModel): ApiModel
}