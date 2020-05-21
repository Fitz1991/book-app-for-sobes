package com.infoshell.data.repository.user

import com.infoshell.data.response.BaseResponse
import com.infoshell.data.response.data.RegisteredUserData
import com.infoshell.data.response.data.UserData
import com.infoshell.domain.entity.LoginCredential
import com.infoshell.domain.entity.RegisterCredential
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response

interface UserRemoteSource {

    fun createSession(): Single<Response<BaseResponse<UserData>>>

    fun login(loginCredential: LoginCredential): Single<Response<BaseResponse<UserData>>>

    fun logout(loginCredential: LoginCredential): Completable

    fun register(registerCredential: RegisterCredential): Single<Response<BaseResponse<RegisteredUserData>>>
}