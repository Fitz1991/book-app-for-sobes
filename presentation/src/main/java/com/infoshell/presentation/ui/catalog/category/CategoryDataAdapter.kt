package com.infoshell.presentation.ui.catalog.category

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.infoshell.book24.R
import com.infoshell.book24.databinding.ItemCategoryBinding
import com.infoshell.presentation.enity.DisplayCategory
import timber.log.Timber


class CategoryDataAdapter :
    RecyclerView.Adapter<CategoryDataAdapter.CategoriesViewHolder>() {

    private var categories: List<DisplayCategory>? = null
    lateinit var onClickHandler: OnItemClickListener

    companion object{
        interface OnItemClickListener{
            fun handle(view:View, displayCategory: DisplayCategory)
        }
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): CategoriesViewHolder {
        val categoryItemBinding: ItemCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_category, viewGroup, false
        )
        return CategoriesViewHolder(categoryItemBinding)
    }

    override fun onBindViewHolder(
        @NonNull categoriesViewHolder: CategoriesViewHolder, i: Int
    ) {
        val currentCategory: DisplayCategory = categories!![i]
        categoriesViewHolder.categoryItemBinding.category = currentCategory
        categoriesViewHolder.categoryItemBinding.onClickHandler = onClickHandler
        categoriesViewHolder.categoryItemBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (categories != null) {
            categories!!.size
        } else {
            0
        }
    }

    fun setCategoriesList(categories: List<DisplayCategory>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    inner class CategoriesViewHolder(@NonNull val categoryItemBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(categoryItemBinding.root)


}