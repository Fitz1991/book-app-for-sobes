package com.infoshell.data.response.data

import com.google.gson.annotations.SerializedName

class  BaseDataWithMeta<Items>(
    @SerializedName("META") val meta: MetaData, items: List<Items>
) : BaseData<Items>(items)
