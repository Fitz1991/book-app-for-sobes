package com.infoshell.data.network

import com.infoshell.data.exceptions.ApiException
import com.infoshell.data.response.BaseResponse
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import retrofit2.Response

class SingleErrorTransformer<Body, Stream : Response<BaseResponse<Body>>> :
    SingleTransformer<Stream, Stream> {

    override fun apply(upstream: Single<Stream>): SingleSource<Stream> {
        return upstream.flatMap {
            //code >= 200 || code < 300
            if (!it.isSuccessful) return@flatMap Single.error<Stream>(mapApiExceptionFactory(it))

            val body = it.body()
            if (body != null && body.isError()) {
                return@flatMap Single.error<Stream>(ApiException(body.getFirstError()))
            }
            return@flatMap Single.just(it)
        }
    }
}

private fun mapApiExceptionFactory(response: Response<*>): ApiException {
    return try {
        val serverMessage = response.errorBody()
            ?.string()
            ?.let { NetworkProvider.provideGson().fromJson(it, BaseResponse::class.java) }

        ApiException(serverMessage?.getFirstError())
    } catch (error: Exception) {
        ApiException("")
    }
}
