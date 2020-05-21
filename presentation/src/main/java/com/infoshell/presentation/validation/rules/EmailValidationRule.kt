package com.infoshell.presentation.validation.rules

import android.util.Patterns

class EmailValidationRule(errorMessage: String) : BaseValidationRule(errorMessage) {

    override fun isValid(text: String?) = !text.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(text).matches()
}