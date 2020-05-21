package com.infoshell.presentation.ui.catalog.category_products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.infoshell.book24.R
import com.infoshell.book24.databinding.ItemCategoryProductBinding
import com.infoshell.presentation.enity.DisplayProduct


class CategoryProductsAdapter :
    PagedListAdapter<DisplayProduct, CategoryProductsAdapter.MyViewHolder>(DisplayProduct.DIFF_CALLBACK) {

    lateinit var onClickHandler: OnItemClickListener

    interface OnItemClickListener {
        fun handle(view: View, displayProduct: DisplayProduct, actionType: ActionType)
    }

    enum class ActionType { ITEM_CLICK, ADD_TO_BASKET }

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding: ItemCategoryProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category_product, parent, false
        )

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(@NonNull holder: MyViewHolder, position: Int) {
        holder.binding.onClickHandler = onClickHandler
        holder.binding.product = getItem(position)
    }

    inner class MyViewHolder(var binding: ItemCategoryProductBinding) :
        RecyclerView.ViewHolder(binding.root)
}