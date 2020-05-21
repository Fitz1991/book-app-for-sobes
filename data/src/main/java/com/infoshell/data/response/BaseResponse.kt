package com.infoshell.data.response

import com.google.gson.annotations.SerializedName

class BaseResponse<Data>(
    @SerializedName("CODE") val code: Int,
    @SerializedName("STATUS") val status: String,
    @SerializedName("DATA") val data: Data?,
    @SerializedName("ERRORS") val backendErrors: MutableList<BackendError> = mutableListOf()
) {

    fun isError() = !status.equals("OK", true) || !backendErrors.isNullOrEmpty()

    fun getFirstError(): String {
        val exception = backendErrors.first().backendExceptions?.first() ?: throw IllegalStateException()
        return exception.message
    }
}

class BackendError(
    @SerializedName("EXCEPTION") val backendExceptions: List<BackendException>?
)

class BackendException(
    @SerializedName("message") val message: String
)