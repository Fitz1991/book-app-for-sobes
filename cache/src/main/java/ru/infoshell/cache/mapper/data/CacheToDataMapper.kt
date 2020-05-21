package ru.infoshell.cache.mapper.data

interface CacheToDataMapper<in CacheModel, out ApiModel> {

    fun mapToData(cacheModel: CacheModel): ApiModel
}