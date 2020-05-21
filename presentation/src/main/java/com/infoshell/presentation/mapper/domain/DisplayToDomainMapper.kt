package com.infoshell.presentation.mapper.domain

interface DisplayToDomainMapper<in DisplayModel, out DomainModel> {

    fun mapToApi(displayModel: DisplayModel): DomainModel
}