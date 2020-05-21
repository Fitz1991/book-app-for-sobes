package ru.infoshell.cache.di

import com.infoshell.data.entity.ApiUser
import com.infoshell.data.repository.user.UserStorageSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.infoshell.cache.PreferencesHelper
import ru.infoshell.cache.UserStorageSourceImpl
import ru.infoshell.cache.entity.CachedUser
import ru.infoshell.cache.mapper.cache.DataToCacheMapper
import ru.infoshell.cache.mapper.cache.UserCacheMapper
import ru.infoshell.cache.mapper.data.CacheToDataMapper
import ru.infoshell.cache.mapper.data.UserDataMapper


private const val USER_CACHE_MAPPER = "USER_CACHE_MAPPER"
private const val USER_DATA_MAPPER = "USER_DATA_MAPPER"

val storageModule = module {

    single {
        UserStorageSourceImpl(
            get(),
            get(named(USER_CACHE_MAPPER)),
            get(named(USER_DATA_MAPPER))
        ) as UserStorageSource
    }

    single { PreferencesHelper(androidApplication()) }

    single(named(USER_CACHE_MAPPER)) { UserCacheMapper() as DataToCacheMapper<ApiUser, CachedUser> }

    single(named(USER_DATA_MAPPER)) { UserDataMapper() as CacheToDataMapper<CachedUser, ApiUser> }
}