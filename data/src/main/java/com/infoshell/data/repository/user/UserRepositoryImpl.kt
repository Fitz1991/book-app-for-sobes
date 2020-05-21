package com.infoshell.data.repository.user

import com.infoshell.data.entity.ApiUser
import com.infoshell.data.mapper.domain.ApiToDomainMapper
import com.infoshell.data.response.BaseResponse
import com.infoshell.data.response.data.RegisteredUserData
import com.infoshell.data.response.data.UserData
import com.infoshell.domain.entity.*
import com.infoshell.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response

class UserRepositoryImpl(
    private val remoteSource: UserRemoteSource,
    private val storageSource: UserStorageSource,
    private val loginDomainMapper: ApiToDomainMapper<Response<BaseResponse<UserData>>, LoginState>,
    private val registerDomainMapper: ApiToDomainMapper<Response<BaseResponse<RegisteredUserData>>, RegisterState>,
    private val userDomainMapper: ApiToDomainMapper<ApiUser, com.infoshell.domain.entity.UserData>
) : UserRepository {

    override fun getUser(): com.infoshell.domain.entity.UserData? {
        val user = storageSource.getUser()
        return if (user != null) userDomainMapper.mapToDomain(user) else null
    }

    override fun checkUserAuthorized(): Boolean {
        return storageSource.checkUser()
    }

    override fun createSession(): Single<LoginState> {
        return remoteSource.createSession()
            .map {
                cacheUser(it)
                loginDomainMapper.mapToDomain(it)
            }
    }

    override fun login(credential: LoginCredential): Single<LoginState> {
        return remoteSource.login(credential)
            .map {
                cacheUser(it)
                loginDomainMapper.mapToDomain(it)
            }
    }

    override fun logout(credential: LoginCredential): Completable {
        return remoteSource.logout(credential)
    }

    override fun register(credential: RegisterCredential): Single<RegisterState> {
        return remoteSource.register(credential)
            .map { registerDomainMapper.mapToDomain(it) }
    }

    private fun cacheUser(response: Response<BaseResponse<UserData>>) {
        val userData = response.body()?.data
        if (userData != null) {
            storageSource.saveUser(userData.user)
        }
    }
}