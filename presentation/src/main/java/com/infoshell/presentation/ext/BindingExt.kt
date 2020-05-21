package com.infoshell.presentation.ext

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.infoshell.book24.R
import com.infoshell.presentation.ui.custom.DividerItemDecoratorWithoutLastItem
import com.infoshell.presentation.ui.custom.interceptors.InterceptLinearLayout
import com.squareup.picasso.Picasso

private const val ALPHA_ENABLE = 1f
private const val ALPHA_DISABLE = 0.5f

@BindingAdapter("errorMessage")
fun TextInputLayout.setViewError(error: String?) {
    isErrorEnabled = false
    setError(null)

    if (!error.isNullOrEmpty()) {
        isErrorEnabled = true
        setError(error)
    }
}

@BindingAdapter("android:alpha")
fun View.bindAlpha(enable: Boolean) {
    alpha = if (enable) ALPHA_DISABLE else ALPHA_ENABLE
}

@BindingAdapter("isIntercept")
fun InterceptLinearLayout.bindIntercept(enable: Boolean) {
    setIntercept(enable)
}

@BindingAdapter("loadUrl")
fun ImageView.loadImage(url: String?) {
    if (url == null || url == "") {
        Picasso.get().load(R.drawable.ic_placeholder)
            .placeholder(R.drawable.ic_placeholder).into(this)
    } else {
        Picasso.get().load(url).error(R.drawable.ic_placeholder)
            .placeholder(R.drawable.ic_placeholder).into(this)
    }
}

@BindingAdapter("strikeThrough")
fun TextView.strikeThrough(strikeThrough: Boolean) {
    paintFlags = if (strikeThrough) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}

@BindingAdapter(value = ["dividerDecoration", "dividerOrientation"], requireAll = false)
fun RecyclerView.dividerDecoration(dividerResId: Drawable, orientation: Int?) {
    val dividerItemDecoration =
        DividerItemDecoratorWithoutLastItem(
            context, orientation ?: LinearLayoutManager.VERTICAL
        )
    dividerItemDecoration.setDrawable(dividerResId)
    addItemDecoration(dividerItemDecoration)
}