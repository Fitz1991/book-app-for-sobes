package ru.infoshell.cache

import com.infoshell.data.entity.ApiUser
import com.infoshell.data.repository.user.UserStorageSource
import ru.infoshell.cache.entity.CachedUser
import ru.infoshell.cache.mapper.cache.DataToCacheMapper
import ru.infoshell.cache.mapper.data.CacheToDataMapper

class UserStorageSourceImpl(
    private val preferenceHelper: PreferencesHelper,
    private val userCacheMapper: DataToCacheMapper<ApiUser, CachedUser>,
    private val userDataMapper: CacheToDataMapper<CachedUser, ApiUser>
) : UserStorageSource {

    override fun getUser(): ApiUser? {
        val user = preferenceHelper.cachedUser
        return if (user != null) userDataMapper.mapToData(user) else null
    }

    override fun saveUser(user: ApiUser) {
        preferenceHelper.cachedUser = userCacheMapper.mapToCache(user)
    }

    override fun checkUser(): Boolean {
        return preferenceHelper.isUserAuthorized
    }
}