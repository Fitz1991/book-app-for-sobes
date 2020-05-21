package com.infoshell.data.entity

import com.google.gson.annotations.SerializedName

class ApiPicture(
    @SerializedName("SRC") val src: String,
    @SerializedName("HEIGHT") val height: Int?,
    @SerializedName("WIDTH") val width: Int?,
    @SerializedName("SIZES") val sizes: List<Size>?
)

class Size(
    @SerializedName("WEBP") val webp: String,
    @SerializedName("SRC") val src: String,
    @SerializedName("RESIZE_TYPE") val resizeType: String
)