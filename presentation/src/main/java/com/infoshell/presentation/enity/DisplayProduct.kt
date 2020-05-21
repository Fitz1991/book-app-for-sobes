package com.infoshell.presentation.enity

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.infoshell.domain.entity.ProductPicture
import com.infoshell.domain.entity.Review
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class DisplayProduct(
    val id: String,
    val isbn: String,
    val author: String,
    val name: String,
    val image: String,
    val price: Double,
    val fullPrice: Double,
    val weight: Double = 0.0,
    val totalQuantity: Int = 0,
    val promo: Double,
    val images: @RawValue List<ProductPicture>,
    val isPromoActive: Boolean = false,
    val isAvailabile: Boolean = false,
    val detailText: String = "",
    val prodText: String = "",
    val rating: Int = 0,
    val countReviews: Int = 0,
    val reviews: @RawValue List<Review>,
    val numberOfPurchases: Int = 0
) : Parcelable {

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<DisplayProduct> =
            object : DiffUtil.ItemCallback<DisplayProduct>() {
                override fun areItemsTheSame(
                    oldItem: DisplayProduct,
                    newItem: DisplayProduct
                ): Boolean {
                    return oldItem.image.equals(newItem.image)
                }

                override fun areContentsTheSame(
                    oldItem: DisplayProduct,
                    newItem: DisplayProduct
                ): Boolean {
                    return oldItem.equals(newItem)
                }
            }
    }

    override fun equals(other: Any?): Boolean {
        if (other === this)
            return true
        val displayProduct = other as DisplayProduct
        return displayProduct.image == this.image
    }
}


