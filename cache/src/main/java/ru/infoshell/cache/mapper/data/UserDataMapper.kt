package ru.infoshell.cache.mapper.data

import com.infoshell.data.entity.ApiUser
import ru.infoshell.cache.entity.CachedUser

class UserDataMapper : CacheToDataMapper<CachedUser, ApiUser> {

    override fun mapToData(cacheModel: CachedUser): ApiUser {
        return ApiUser(
            cacheModel.id,
            cacheModel.email,
            cacheModel.firstName,
            cacheModel.lastName,
            cacheModel.secondName,
            cacheModel.phone
        )
    }
}