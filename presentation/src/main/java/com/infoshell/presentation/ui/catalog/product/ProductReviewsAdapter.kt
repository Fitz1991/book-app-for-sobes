package com.infoshell.presentation.ui.catalog.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.infoshell.book24.R
import com.infoshell.book24.databinding.ItemProductReviewsBinding
import com.infoshell.domain.entity.Review
import com.infoshell.presentation.enity.DisplayCategory


class ProductReviewsAdapter :
    RecyclerView.Adapter<ProductReviewsAdapter.ProductReviewsViewHolder>() {

    private var reviews: List<Review>? = null
    lateinit var onClickHandler: OnItemClickListener

    companion object{
        interface OnItemClickListener{
            fun handle(view:View, displayCategory: DisplayCategory)
        }
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): ProductReviewsViewHolder {
        val categoryItemBinding: ItemProductReviewsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_product_reviews, viewGroup, false
        )
        return ProductReviewsViewHolder(categoryItemBinding)
    }

    override fun onBindViewHolder(
        @NonNull productReviewsViewHolder: ProductReviewsViewHolder, i: Int
    ) {
        val currentReview: Review = reviews!![i]
        productReviewsViewHolder.itemProductReviewsBinding.review = currentReview
        productReviewsViewHolder.itemProductReviewsBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (reviews != null) {
            reviews!!.size
        } else {
            0
        }
    }

    fun setReviewsList(reviews: List<Review>) {
        this.reviews = reviews
        notifyDataSetChanged()
    }

    inner class ProductReviewsViewHolder(@NonNull val itemProductReviewsBinding: ItemProductReviewsBinding) :
        RecyclerView.ViewHolder(itemProductReviewsBinding.root)
}