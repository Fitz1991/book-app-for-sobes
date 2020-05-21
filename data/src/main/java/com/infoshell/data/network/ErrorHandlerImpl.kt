package com.infoshell.data.network

import com.infoshell.data.exceptions.ApiException
import com.infoshell.domain.entity.ErrorEntity
import com.infoshell.domain.entity.ErrorHandler
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class ErrorHandlerImpl: ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        return when(throwable) {
            is IOException -> ErrorEntity.Network(throwable)
            is ApiException -> ErrorEntity.Api(throwable)
            is HttpException -> {
                when(throwable.code()) {
                    // not found
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound(throwable)

                    // access denied
                    HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied(throwable)

                    // unavailable service
                    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable(throwable)

                    // all the others will be treated as unknown error
                    else -> ErrorEntity.Unknown(throwable)
                }
            }
            else -> ErrorEntity.Unknown(throwable)
        }
    }
}