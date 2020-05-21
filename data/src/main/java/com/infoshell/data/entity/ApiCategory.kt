package com.infoshell.data.entity

import com.google.gson.annotations.SerializedName

class ApiCategory(
    @SerializedName("ID") val id: String,
    @SerializedName("PARENT_ID") val parentId: String,
    @SerializedName("CODE") val code: String,
    @SerializedName("NAME") val name: String,
    @SerializedName("SWG_ICON") val swgIcon: String,
    @SerializedName("PICTURE") val picture: ApiPicture?,
    @SerializedName("ICON") val icon: String?,
    @SerializedName("META") val meta: ApiMeta
)