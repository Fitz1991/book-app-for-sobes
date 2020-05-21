package com.infoshell.presentation.mapper.display

interface DomainToDisplayMapper<in DomainModel, out DisplayModel> {

    fun mapToDisplay(domainModel: DomainModel): DisplayModel
}