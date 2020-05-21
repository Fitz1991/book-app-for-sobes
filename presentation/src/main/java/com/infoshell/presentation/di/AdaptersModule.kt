package com.infoshell.presentation.di

import com.infoshell.presentation.ui.basket.BasketProductsAdapter
import com.infoshell.presentation.ui.catalog.category.CategoryDataAdapter
import com.infoshell.presentation.ui.catalog.category_products.CategoryProductsAdapter
import com.infoshell.presentation.ui.catalog.product.ProductCoverAdapter
import com.infoshell.presentation.ui.catalog.product.ProductReviewsAdapter
import com.infoshell.presentation.ui.home.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val PROMOTION_BANNER_ADAPTER = "PROMOTION_BANNER_ADAPTER"
const val CATEGORY_CARD_ADAPTER = "CATEGORY_CARD_ADAPTER"
const val AUTHORS_CARD_ADAPTER = "AUTHORS_CARD_ADAPTER"
const val PRODUCT_NOVELTY_ADAPTER = "PRODUCT_NOVELTY_ADAPTER"
const val PRODUCT_RECOMMENDATIONS_ADAPTER = "PRODUCT_RECOMMENDATIONS_ADAPTER"
const val PRODUCT_BESTSELLERS_ADAPTER = "PRODUCT_BESTSELLERS_ADAPTER"
const val COMPILATIONS_CARD_ADAPTER = "COMPILATIONS_CARD_ADAPTER"
const val PRODUCT_ADAPTER = "PRODUCT_ADAPTER"
const val BASKET_PRODUCTS_ADAPTER = "BASKET_PRODUCTS_ADAPTER"
const val PRODUCT_COVER_ADAPTER = "PRODUCT_COVER_ADAPTER"
const val PRODUCT_REVIEWS_ADAPTER = "PRODUCT_REVIEWS_ADAPTER"

val recyclerViewAdaptersModule = module {

    //---------Adapters---------
    single { CategoryDataAdapter() }
    single(named(PRODUCT_ADAPTER)) { CategoryProductsAdapter() }
    single(named(PRODUCT_RECOMMENDATIONS_ADAPTER)) { ProductsAdapter() }
    single(named(PRODUCT_NOVELTY_ADAPTER)) { ProductsAdapter() }
    single(named(PRODUCT_BESTSELLERS_ADAPTER)) { ProductsAdapter() }
    single(named(CATEGORY_CARD_ADAPTER)) { CategoryCardAdapter() }
    single(named(AUTHORS_CARD_ADAPTER)) { AuthorCardAdapter() }
    single(named(PROMOTION_BANNER_ADAPTER)) { PromoBannerAdapter(androidContext()) }
    single(named(COMPILATIONS_CARD_ADAPTER)) { CompilationAdapter() }
    single(named(BASKET_PRODUCTS_ADAPTER)) { BasketProductsAdapter() }
    single(named(PRODUCT_COVER_ADAPTER)) { ProductCoverAdapter(androidContext()) }
    single(named(PRODUCT_REVIEWS_ADAPTER)) { ProductReviewsAdapter() }
}