package com.infoshell.presentation.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt

class ClickableSpanResolver(private val fullText: String) {

    private val agreementSpan = SpannableString(fullText)

    fun add(path: String, @ColorInt color: Int, onClick: () -> Unit): ClickableSpanResolver {
        val start = fullText.indexOf(path)
        val end = start + path.length

        agreementSpan.setSpan(
            PathClickableSpan { onClick() }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        agreementSpan.setSpan(
            ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return this
    }

    fun build() = agreementSpan
}