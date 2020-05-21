package com.infoshell.data.response

import com.google.gson.annotations.SerializedName

class BaseResponseReviews<Data>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: List<Data>?
)