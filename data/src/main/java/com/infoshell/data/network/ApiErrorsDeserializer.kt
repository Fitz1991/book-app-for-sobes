package com.infoshell.data.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import com.infoshell.data.response.BackendError
import java.lang.reflect.Type

class ApiErrorsDeserializer : JsonDeserializer<MutableList<BackendError>> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext
    ): MutableList<BackendError> {
        if (json == null) {
            throw JsonParseException("Json is null")
        }

        if (json.isJsonObject) {
            val singleError = context.deserialize<BackendError>(json, genericType<BackendError>())
            return arrayListOf(singleError)
        }

        if (json.isJsonArray) {
            return json.asJsonArray
                .map { context.deserialize<BackendError>(it, genericType<BackendError>()) }
                .toMutableList<BackendError>()
        }

        throw JsonParseException("Json errors: $json")
    }

    private inline fun <reified T> genericType(): Type = object : TypeToken<T>() {}.type
}