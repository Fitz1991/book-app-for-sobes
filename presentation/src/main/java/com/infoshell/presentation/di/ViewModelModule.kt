package com.infoshell.presentation.di

import com.infoshell.domain.entity.*
import com.infoshell.presentation.enity.*
import com.infoshell.presentation.mapper.display.*
import com.infoshell.presentation.mapper.domain.DisplayToDomainMapper
import com.infoshell.presentation.mapper.domain.LoginCredentialDisplayMapper
import com.infoshell.presentation.mapper.domain.ProductDomainMapper
import com.infoshell.presentation.mapper.domain.RegisterCredentialDisplayMapper
import com.infoshell.presentation.ui.basket.BasketViewModel
import com.infoshell.presentation.ui.catalog.category.CategoryViewModel
import com.infoshell.presentation.ui.catalog.category_products.CategoryProductsDataSourceFactory
import com.infoshell.presentation.ui.catalog.category_products.CategoryProductsViewModel
import com.infoshell.presentation.ui.catalog.product.ProductViewModel
import com.infoshell.presentation.ui.home.HomeViewModel
import com.infoshell.presentation.ui.profile.ProfileViewModel
import com.infoshell.presentation.ui.profile.unauthorized.login.LoginViewModel
import com.infoshell.presentation.ui.profile.unauthorized.registration.RegistrationViewModel
import io.reactivex.disposables.CompositeDisposable
import com.infoshell.presentation.ui.search.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val LOGIN_CREDENTIAL_DISPLAY_MAPPER = "LOGIN_CREDENTIAL_DISPLAY_MAPPER"
private const val REGISTER_CREDENTIAL_DISPLAY_MAPPER = "REGISTER_CREDENTIAL_DISPLAY_MAPPER"
private const val USER_DATA_DISPLAY_MAPPER = "USER_DATA_DISPLAY_MAPPER"
private const val PRODUCT_DISPLAY_MAPPER = "PRODUCT_DISPLAY_MAPPER"
private const val AUTHOR_DISPLAY_MAPPER = "AUTHOR_DISPLAY_MAPPER"
private const val CATEGORY_DISPLAY_MAPPER = "CATEGORY_DISPLAY_MAPPER"
private const val PROMOTION_DISPLAY_MAPPER = "PROMOTION_DISPLAY_MAPPER"
private const val COMPILATION_DISPLAY_MAPPER = "COMPILATION_DISPLAY_MAPPER"
private const val BASKET_DISPLAY_MAPPER = "BASKET_DISPLAY_MAPPER"
private const val PRODUCT_DISPLAY_DOMAIN_MAPPER = "PRODUCT_DISPLAY_DOMAIN_MAPPER"
private const val POPULAR_REQUEST_DISPLAY_MAPPER = "POPULAR_REQUEST_DISPLAY_MAPPER"

val viewModelModule = module {

    //---------ViewModels---------
    viewModel { ProfileViewModel(get(), get(named(USER_DATA_DISPLAY_MAPPER)), androidApplication()) }
    viewModel { LoginViewModel(get(), get(named(LOGIN_CREDENTIAL_DISPLAY_MAPPER)), androidApplication()) }
    viewModel { RegistrationViewModel(get(), get(named(REGISTER_CREDENTIAL_DISPLAY_MAPPER)), androidApplication()) }
    viewModel { HomeViewModel(get(), get(named(PRODUCT_DISPLAY_MAPPER)), get(named(CATEGORY_DISPLAY_MAPPER)), get(named(AUTHOR_DISPLAY_MAPPER)), get(named(PROMOTION_DISPLAY_MAPPER)), get(named(COMPILATION_DISPLAY_MAPPER)), androidApplication()) }
    viewModel { CategoryViewModel(get(), get(named(CATEGORY_DISPLAY_MAPPER)), androidApplication()) }
    viewModel { params -> CategoryProductsViewModel(CategoryProductsDataSourceFactory(params, get(named(PRODUCT_DISPLAY_MAPPER)), get(named(PRODUCT_DISPLAY_DOMAIN_MAPPER))), androidApplication()) }
    viewModel { BasketViewModel(get(), get(named(PRODUCT_DISPLAY_MAPPER)), get(named(BASKET_DISPLAY_MAPPER)), get(named(PRODUCT_DISPLAY_DOMAIN_MAPPER)), androidApplication()) }
    viewModel { ProductViewModel(get(), get(named(PRODUCT_DISPLAY_MAPPER)), get(named(PRODUCT_DISPLAY_DOMAIN_MAPPER)), androidApplication()) }
    viewModel { SearchViewModel(get(), get(named(POPULAR_REQUEST_DISPLAY_MAPPER)), androidApplication()) }

    //---------Mappers---------
    single(named(PRODUCT_DISPLAY_MAPPER)) { ProductDisplayMapper() as DomainToDisplayMapper<Product, DisplayProduct> }
    single(named(PRODUCT_DISPLAY_DOMAIN_MAPPER)) { ProductDomainMapper() as DisplayToDomainMapper<DisplayProduct, Product> }
    single(named(LOGIN_CREDENTIAL_DISPLAY_MAPPER)) { LoginCredentialDisplayMapper() as DisplayToDomainMapper<DisplayLoginCredential, LoginCredential> }
    single(named(REGISTER_CREDENTIAL_DISPLAY_MAPPER)) { RegisterCredentialDisplayMapper() as DisplayToDomainMapper<DisplayRegisterCredential, RegisterCredential> }
    single(named(USER_DATA_DISPLAY_MAPPER)) { UserDisplayMapper() as DomainToDisplayMapper<UserData, DisplayUserData> }
    single(named(AUTHOR_DISPLAY_MAPPER)) { AuthorsDisplayMapper() as DomainToDisplayMapper<Author, DisplayAuthor> }
    single(named(PROMOTION_DISPLAY_MAPPER)) { PromotionDisplayMapper() as DomainToDisplayMapper<Promotion, DisplayPromotion> }
    single(named(COMPILATION_DISPLAY_MAPPER)) { CompilationDisplayMapper() as DomainToDisplayMapper<Compilation, DisplayCompilation> }
    single(named(CATEGORY_DISPLAY_MAPPER)) { CategoriesDisplayMapper() as DomainToDisplayMapper<Category, DisplayCategory> }
    single(named(BASKET_DISPLAY_MAPPER)) { BasketDisplayMapper() as DomainToDisplayMapper<Basket, DisplayBasket> }
    single(named(POPULAR_REQUEST_DISPLAY_MAPPER)) { PopularRequestDisplayMapper() as DomainToDisplayMapper<PopularRequest, DisplayPopularRequest> }

    //---------Other---------
//    single { CompositeDisposable() }
}