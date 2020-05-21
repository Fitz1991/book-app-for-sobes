package com.infoshell.presentation.validation.rules

class PhoneValidationRule(errorMessage: String) : BaseValidationRule(errorMessage) {

    override fun isValid(text: String?) = !text.isNullOrEmpty() && !text.contains("X")
}