package com.infoshell.presentation.validation.rules

interface ValidationRule {

    fun isValid(text: String?): Boolean
}