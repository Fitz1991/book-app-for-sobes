package com.infoshell.data.entity

import com.google.gson.annotations.SerializedName

class ApiProduct(
    @SerializedName("ID") val id: String,
    @SerializedName("CODE") val code: String,
    @SerializedName("NAME") val name: String,
    @SerializedName("AUTHOR_ID") val authorId: List<String>,
    @SerializedName("CATEGORY_ID") val categoryId: String,
    @SerializedName("SERIE_ID") val serieId: String,
    @SerializedName("PUBLISHING_HOUSE_ID") val publishingHouseId: String,
    @SerializedName("PRICE") val price: Double,
    @SerializedName("PRICE_FORMAT") val priceFormet: String,
    @SerializedName("OLD_PRICE") val oldPrice: Double,
    @SerializedName("OLD_PRICE_FORMAT") val oldPriceFormat: String,
    @SerializedName("DISCOUNT_PERCENT") val discountPercent: Int,
    @SerializedName("AVAILABILITY") val availability: String,
    @SerializedName("PRE_ORDER") val preOrder: String,
    @SerializedName("PRE_ORDER_DATE") val preOrderDate: String?,
    @SerializedName("PRE_ORDER_RAW") val preOrderRaw: String,
    @SerializedName("WEIGHT") val weight: Double,
    @SerializedName("WEIGHT_FORMAT") val weightFormat: String,
    @SerializedName("ISBN") val isbn: String,
    @SerializedName("AGE_LIMIT") val ageLimit: Int,
    @SerializedName("DATE_LAST_EDITION") val dateLastEdittion: String,
    @SerializedName("DATE_FIRST_EDITION") val dateFirstEdittion: String,
    @SerializedName("PAGE_QUANTITY") val pageQuantity: Int,
    @SerializedName("COVER") val cover: String?,
    @SerializedName("PAPER") val paper: String?,
    @SerializedName("FORMAT") val format: String,
    @SerializedName("EDITION") val edition: Int,
    @SerializedName("DETAIL_TEXT") val detailText: String,
    @SerializedName("ACTIVE") val active: String,
    @SerializedName("BESTSELLER") val bestseller: String,
    @SerializedName("FRAGMENT") val fragment: String,
    @SerializedName("NEW") val new: String,
    @SerializedName("NOMCODE") val nomCode: String,
    @SerializedName("NUMBER_OF_ORDERS") val numberOfOrders: Int,
    @SerializedName("NUMBER_OF_PURCHASES") val numberOfPurchases: Int,
    @SerializedName("PROD_TEXT") val prodText: String,
    @SerializedName("QUANTITY") val quantity: Int,
    @SerializedName("SALE_CLOSE") val saleClose: String,
    @SerializedName("SORT") val sort: Int,
    @SerializedName("SEARCH_TAGS") val searchTag: String,
    @SerializedName("YEAR") val year: String,
    @SerializedName("REDACTION") val redaction: String,
    @SerializedName("TOP_AUTHOR_BOOK") val topAuthorBook: String,
    @SerializedName("GUID_PROD") val guigProd: String,
    @SerializedName("REDACTION_ID") val redactionId: String,
    @SerializedName("PUBLISHER_BRAND_ID") val publisherBrandId: String,
    @SerializedName("NOM_FOLDER_ID") val nomFolderId: String,
    @SerializedName("MATERIAL_TYPE_ID") val materialTypeId: String,
    @SerializedName("LICENSE") val license: String,
    @SerializedName("PROMO_DISABLED") val promoDisabled: String,
    @SerializedName("THEME_ID") val themeId: String,
    @SerializedName("NICHE_ID") val nicheId: String,
    @SerializedName("NOT_UPDATE") val notUpdate: String,
    @SerializedName("SUPPLIER_ID") val suppleierId: String,
    @SerializedName("ALREADY_PURCHASED") val alredyPurchased: String,
    @SerializedName("RDC") val rdc: Int,
    @SerializedName("CART_RULE_CODE") val cardRuleCode: List<String>,
    @SerializedName("RELATIONS") val relations: List<Relation>,
    @SerializedName("PRODUCT_TYPE") val productType: Int,
    @SerializedName("IMAGES") val images: List<ApiPicture>,
    @SerializedName("META") val meta: ApiMeta
) {

    fun isAvailabile() = isTrue(availability)
    fun isPreOrder() = isTrue(preOrder)
    fun isPreOrderRaw() = isTrue(preOrderRaw)
    fun isActive() = isTrue(active)
    fun isBestseller() = isTrue(bestseller)
    fun isFragment() = isTrue(fragment)
    fun isNew() = isTrue(new)
    fun isSaleClose() = isTrue(saleClose)
    fun isTopAuthorBook() = isTrue(topAuthorBook)
    fun isPromoDisabled() = isTrue(promoDisabled)
    fun isNotUpdate() = isTrue(notUpdate)
    fun isAlredyPurchased() = isTrue(alredyPurchased)

    private fun isTrue(str: String) = str.equals("y", ignoreCase = true)


}