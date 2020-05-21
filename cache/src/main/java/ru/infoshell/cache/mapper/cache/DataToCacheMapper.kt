package ru.infoshell.cache.mapper.cache

interface DataToCacheMapper<in ApiModel, out CacheModel> {

    fun mapToCache(cacheModel: ApiModel): CacheModel
}