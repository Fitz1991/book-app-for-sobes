package com.infoshell.presentation.validation.rules

class EmptyValidationRule(errorMessage: String) : BaseValidationRule(errorMessage) {

    override fun isValid(text: String?) = !text.isNullOrEmpty()
}