package com.infoshell.data.repository.user

import com.infoshell.data.mapper.api.DomainToApiMapper
import com.infoshell.data.network.BookApiService
import com.infoshell.data.network.SingleErrorTransformer
import com.infoshell.data.response.BaseResponse
import com.infoshell.data.response.data.RegisteredUserData
import com.infoshell.data.response.data.UserData
import com.infoshell.domain.entity.LoginCredential
import com.infoshell.domain.entity.RegisterCredential
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response

class UserApiSource(
    private val apiService: BookApiService,
    private val loginApiMapper: DomainToApiMapper<LoginCredential, Map<String, String>>,
    private val registerMapper: DomainToApiMapper<RegisterCredential, Map<String, String>>
) : UserRemoteSource {

    override fun createSession(): Single<Response<BaseResponse<UserData>>> {
        return apiService.createSession()
            .compose(SingleErrorTransformer<UserData, Response<BaseResponse<UserData>>>())
    }

    override fun login(loginCredential: LoginCredential): Single<Response<BaseResponse<UserData>>> {
        return apiService.login(loginApiMapper.mapToApi(loginCredential))
            .compose(SingleErrorTransformer<UserData, Response<BaseResponse<UserData>>>())
    }

    override fun logout(loginCredential: LoginCredential): Completable {
        return Completable.complete()
    }

    override fun register(registerCredential: RegisterCredential): Single<Response<BaseResponse<RegisteredUserData>>> {
        return apiService.register(registerMapper.mapToApi(registerCredential))
            .compose(SingleErrorTransformer<RegisteredUserData, Response<BaseResponse<RegisteredUserData>>>())
    }
}