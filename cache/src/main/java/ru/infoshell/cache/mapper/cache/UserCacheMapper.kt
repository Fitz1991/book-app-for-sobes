package ru.infoshell.cache.mapper.cache

import com.infoshell.data.entity.ApiUser
import ru.infoshell.cache.entity.CachedUser

class UserCacheMapper : DataToCacheMapper<ApiUser, CachedUser> {

    override fun mapToCache(cacheModel: ApiUser): CachedUser {
        return CachedUser(
            cacheModel.id,
            cacheModel.email,
            cacheModel.firstName,
            cacheModel.lastName,
            cacheModel.secondName,
            cacheModel.phone
        )
    }
}