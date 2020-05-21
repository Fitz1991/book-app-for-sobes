package com.infoshell.presentation.validation.rules

private const val NAME_REGEX = "^[a-z-A-Zа-яА-Я]+(?:\\W[a-z-A-Zа-яА-Я]+)+\$"

class NameValidationRule(errorMessage: String) : BaseValidationRule(errorMessage) {

    override fun isValid(text: String?) = !text.isNullOrEmpty() && text.matches(NAME_REGEX.toRegex())
}