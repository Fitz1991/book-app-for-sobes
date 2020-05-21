package com.infoshell.presentation.ui.catalog.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.infoshell.book24.R
import com.infoshell.domain.entity.ProductPicture
import com.infoshell.presentation.ui.custom.viewpager.LoopingPagerAdapter
import com.squareup.picasso.Picasso


class ProductCoverAdapter(
    context: Context,
    private var imageList: List<ProductPicture> = listOf()
) : LoopingPagerAdapter<ProductPicture>(context, imageList, true) {


    fun refreshList(items: List<ProductPicture>) {
        this.imageList = items
        isInfinite = false
        setItemList(imageList)
    }

    override fun getCount(): Int {
        return super.getCount()
    }

    override fun inflateView(viewType: Int, container: ViewGroup, listPosition: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_product_cover, container, false)
    }

    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        val image: ImageView = convertView.findViewById(R.id.coverIV) as ImageView
        if (imageList[listPosition].src == "") {
            Picasso.get().load(R.drawable.ic_placeholder_horizontal)
                .placeholder(R.drawable.ic_placeholder_horizontal).into(image)
        } else {
            Picasso.get().load(imageList[listPosition].src)
                .error(R.drawable.ic_placeholder_horizontal)
                .placeholder(R.drawable.ic_placeholder_horizontal).into(image)
        }
    }
}