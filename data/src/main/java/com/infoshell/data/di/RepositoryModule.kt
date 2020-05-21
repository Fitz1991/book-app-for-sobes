package com.infoshell.data.di

import com.infoshell.data.entity.ApiAuthor
import com.infoshell.data.entity.ApiCategory
import com.infoshell.data.entity.ApiProduct
import com.infoshell.data.entity.ApiUser
import com.infoshell.data.mapper.api.BasketItemApiMapper
import com.infoshell.data.mapper.api.DomainToApiMapper
import com.infoshell.data.mapper.api.LoginApiMapper
import com.infoshell.data.mapper.api.RegisterApiMapper
import com.infoshell.data.mapper.domain.*
import com.infoshell.data.repository.author.*
import com.infoshell.data.repository.basket.*
import com.infoshell.data.repository.search.*
import com.infoshell.data.repository.category.*
import com.infoshell.data.repository.compilation.*
import com.infoshell.data.repository.product.*
import com.infoshell.data.repository.promotion.*
import com.infoshell.data.repository.user.*
import com.infoshell.data.response.BaseResponse
import com.infoshell.data.response.data.BasketData
import com.infoshell.data.response.data.RegisteredUserData
import com.infoshell.data.response.data.UserData
import com.infoshell.domain.entity.*
import com.infoshell.domain.repository.BasketRepository
import com.infoshell.domain.repository.UserRepository
import com.infoshell.domain.repository.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Response

private const val LOGIN_CREDENTIAL_MAPPER = "LOGIN_CREDENTIAL_MAPPER"
private const val REGISTER_CREDENTIAL_MAPPER = "REGISTER_CREDENTIAL_MAPPER"

private const val LOGIN_DOMAIN_MAPPER = "LOGIN_DOMAIN_MAPPER"
private const val REGISTER_DOMAIN_MAPPER = "REGISTER_DOMAIN_MAPPER"
private const val USER_DOMAIN_MAPPER = "USER_DOMAIN_MAPPER"

private const val PRODUCT_DOMAIN_MAPPER = "PRODUCT_DOMAIN_MAPPER"
private const val CATEGORY_DOMAIN_MAPPER = "CATEGORY_DOMAIN_MAPPER"
private const val AUTHOR_DOMAIN_MAPPER = "AUTHOR_DOMAIN_MAPPER"
private const val META_DOMAIN_MAPPER = "META_DOMAIN_MAPPER"

private const val BASKET_DOMAIN_MAPPER = "BASKET_DOMAIN_MAPPER"
private const val PRODUCT_API_MAPPER = "PRODUCT_API_MAPPER"

val repositoryModule = module {

    //---------RepositoryImplementations---------
    single { UserRepositoryImpl(get(), get(), get(named(LOGIN_DOMAIN_MAPPER)), get(named(REGISTER_DOMAIN_MAPPER)), get(named(USER_DOMAIN_MAPPER))) as UserRepository }
    single { CategoryRepositoryImpl(get()) as CategoryRepository }
    single { ProductRepositoryImpl(get()) as ProductRepository }
    single { BasketRepositoryImpl(get()) as BasketRepository }
    single { AuthorRepositoryImpl(get()) as AuthorRepository }
    single { PromotionRepositoryImpl(get()) as PromotionRepository }
    single { CompilationRepositoryImpl(get()) as CompilationRepository }
    single { SearchRepositoryImpl(get()) as SearchRepository }

    //---------RemoteSources---------
    single { UserApiSource(get(), get(named(LOGIN_CREDENTIAL_MAPPER)), get(named(REGISTER_CREDENTIAL_MAPPER))) as UserRemoteSource }
    single { ProductApiSource(get(), get(named(PRODUCT_DOMAIN_MAPPER)), get(named(META_DOMAIN_MAPPER))) as ProductRemoteSource }
    single { CategoryApiSource(get(), get(named(CATEGORY_DOMAIN_MAPPER))) as CategoryRemoteSource }
    single { BasketApiSource(get(), get(named(BASKET_DOMAIN_MAPPER)), get(named(PRODUCT_API_MAPPER))) as BasketRemoteSource }
    single { AuthorApiSource(get(), get(named(AUTHOR_DOMAIN_MAPPER))) as AuthorRemoteSource }
    single { PromotionApiSource(get()) as PromotionRemoteSource }
    single { CompilationApiSource(get()) as CompilationRemoteSource }
    single { SearchApiSource(get()) as SearchRemoteSource }

    //---------Mappers---------
    single(named(LOGIN_CREDENTIAL_MAPPER)) { LoginApiMapper() as DomainToApiMapper<LoginCredential, Map<String, String>> }
    single(named(REGISTER_CREDENTIAL_MAPPER)) { RegisterApiMapper() as DomainToApiMapper<RegisterCredential, Map<String, String>> }
    single(named(LOGIN_DOMAIN_MAPPER)) { LoginDomainMapper() as ApiToDomainMapper<Response<BaseResponse<UserData>>, LoginState> }
    single(named(REGISTER_DOMAIN_MAPPER)) { RegisterDomainMapper() as ApiToDomainMapper<Response<BaseResponse<RegisteredUserData>>, RegisterState> }
    single(named(USER_DOMAIN_MAPPER)) { UserDomainMapper() as ApiToDomainMapper<ApiUser, com.infoshell.domain.entity.UserData> }
    single(named(PRODUCT_DOMAIN_MAPPER)) { ProductDomainMapper() as ApiToDomainMapper<ApiProduct, Product> }
    single(named(CATEGORY_DOMAIN_MAPPER)) { CategoryDomainMapper() as ApiToDomainMapper<ApiCategory, Category> }
    single(named(AUTHOR_DOMAIN_MAPPER)) { AuthorsDomainMapper() as ApiToDomainMapper<ApiAuthor, Author> }
    single(named(BASKET_DOMAIN_MAPPER)) { BasketDomainMapper() as ApiToDomainMapper<BasketData, Basket> }
    single(named(PRODUCT_API_MAPPER)) { BasketItemApiMapper() as DomainToApiMapper<Product, Map<String, String>> }
    single(named(META_DOMAIN_MAPPER)) { MetaDomainMapper() as ApiToDomainMapper<MetaData, com.infoshell.domain.entity.MetaData> }
}