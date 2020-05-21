package com.infoshell.data.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.infoshell.data.API_URL
import com.infoshell.data.BuildConfig
import com.infoshell.data.response.BackendError
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.net.CookieManager
import java.net.CookiePolicy

object NetworkProvider {

    fun provideBookApiService(retrofit: Retrofit): BookApiService =
        retrofit.create(BookApiService::class.java)

    fun provideRetrofit(okHttpClient: OkHttpClient, vararg converterFactory: Converter.Factory):
            Retrofit {
        val retrofitBuilder = Retrofit.Builder().client(okHttpClient).baseUrl(API_URL)
        converterFactory.forEach { retrofitBuilder.addConverterFactory(it) }
        return retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    }

    fun provideOkHttpClient(): OkHttpClient {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

        val builder = OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(cookieManager))
            .addInterceptor(HttpLoggingInterceptor().apply { level = getLoggingLevel() })

        if (BuildConfig.DEBUG) builder.addInterceptor(OkHttpProfilerInterceptor())

        return builder.build()
    }

    fun provideGsonConverterFactory(gson: Gson): Converter.Factory =
        GsonConverterFactory.create(gson)

    fun provideGson(): Gson = GsonBuilder().registerTypeAdapter(
        genericType<MutableList<BackendError>>(),
        ApiErrorsDeserializer()
    ).create()

    private fun getLoggingLevel() =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE

    private inline fun <reified T> genericType(): Type = object : TypeToken<T>() {}.type
}