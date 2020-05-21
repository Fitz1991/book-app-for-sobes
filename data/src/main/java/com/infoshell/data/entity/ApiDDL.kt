package com.infoshell.data.entity

import com.google.gson.annotations.SerializedName

data class ApiDDL(
    @SerializedName("currency") val currency: String,
    @SerializedName("subtotal") val subtotal: Double,
    @SerializedName("total") val total: Double,
    @SerializedName("lineItems") val lineItems: List<LineItems>,
    @SerializedName("id") val id: Int,
//    @SerializedName("vouchers") val vouchers: List<>,
    @SerializedName("voucherDiscount") val voucherDiscount: Int,
    @SerializedName("checkoutType") val checkoutType: String
)

data class LineItems(
    @SerializedName("product") val product: Product,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("subtotal") val subtotal: Double,
    @SerializedName("totalDiscount") val totalDiscount: Int
)

data class Product(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("unitPrice") val unitPrice: Double,
    @SerializedName("unitSalePrice") val unitSalePrice: Double,
    @SerializedName("category") val category: List<String>,
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("url") val url: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("stock") val stock: Int,
    @SerializedName("author") val author:  String,
    @SerializedName("isTodayDeliveryAvailable") val isTodayDeliveryAvailable: Boolean,
    @SerializedName("rrc") val rrc: Int
)