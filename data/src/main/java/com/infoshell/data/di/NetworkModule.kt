package com.infoshell.data.di

import com.infoshell.data.network.NetworkProvider
import org.koin.dsl.module

val networkModule = module {

    //---------Network---------
    single { NetworkProvider.provideBookApiService(get()) }
    single { NetworkProvider.provideRetrofit(get(), get()) }
    single { NetworkProvider.provideOkHttpClient() }
    single { NetworkProvider.provideGsonConverterFactory(get()) }
    single { NetworkProvider.provideGson() }
}