package com.infoshell.presentation.utils

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

class PathClickableSpan(private val onClick: () -> Unit) : ClickableSpan() {

    override fun onClick(widget: View) = onClick()

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
    }
}