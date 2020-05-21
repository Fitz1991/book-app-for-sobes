package com.infoshell.data.response.data

import com.google.gson.annotations.SerializedName

open class BaseData<Items>(
    @SerializedName("ITEMS") val items: List<Items>
)
