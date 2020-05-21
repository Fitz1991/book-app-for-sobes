package ru.infoshell.cache

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import ru.infoshell.cache.entity.CachedUser

class PreferencesHelper constructor(context: Context) {

    companion object {
        private const val PREF_BUFFER_PACKAGE_NAME = "com.infoshell.book24.preferences"

        private val PREF_KEY_USER = "user_cache"
    }

    private val bufferPref: SharedPreferences

    init {
        bufferPref = context.getSharedPreferences(PREF_BUFFER_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    var cachedUser: CachedUser?
        get() = Gson().fromJson(
            bufferPref.getString(PREF_KEY_USER, null),
            CachedUser::class.java
        )
        set(user) = bufferPref.edit().putString(PREF_KEY_USER, Gson().toJson(user)).apply()

    var isUserAuthorized = false
        get() = bufferPref.contains(PREF_KEY_USER)
}