package com.infoshell.domain.entity

open class Product(
    var id: String,
    var author: String = "",
    var isbn: String,
    var name: String = "",
    var image: String = "",
    var price: Double = 0.0,
    var fullPrice: Double = 0.0,
    var weight: Double = 0.0,
    var totalQuantity: Int = 0,
    var promo: Double = 0.0,
    var images: List<ProductPicture> = listOf(),
    var isPromoActive: Boolean = false,
    var isAvailabile: Boolean = false,
    var detailText: String = "",
    var prodText: String = "",
    var numberOfPurchases: Int = 0,
    var rating: Int = 0,
    var countReviews: Int = 0,
    var reviews: List<Review> = listOf()
)

class ProductPicture(
    val src: String,
    val height: Int? = null,
    val width: Int? = null,
    val sizes: List<Size>? = null
)

class Size(
    val webp: String,
    val src: String,
    val resizeType: String
)

class Review (
    var id:Int?=null,
    var review:String?=null,
    var reviewBrief:String?=null,
    var authorName:String?=null,
    var reviewRating:Int?=null,
    var date:String?=null
)
