package com.infoshell.data.response.data

import com.google.gson.annotations.SerializedName

data class MetaData(
    @SerializedName("SIZE") val size: Int,
    @SerializedName("TOTAL") val total: Int,
    @SerializedName("PAGE") val page: Int
)



