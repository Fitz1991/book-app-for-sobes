package com.infoshell.domain.di

import com.infoshell.domain.usecase.*
import org.koin.dsl.module

val useCaseModule = module {

    //---------UseCases---------
    single { ProfileUseCase(get()) }
    single { LoginUseCase(get()) }
    single { RegistrationUseCase(get()) }
    single { HomeUseCase(get(), get(), get(), get(), get()) }
    single { CategoryUseCase(get()) }
    single { ProductUseCase(get(), get()) }
    single { SearchUseCase(get()) }
    single { BasketUseCase(get(), get(), get()) }
}