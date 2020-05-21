package com.infoshell.presentation.validation

import androidx.databinding.ObservableField
import com.infoshell.presentation.validation.rules.BaseValidationRule

class FieldValidation(
    private val errorField: ObservableField<String>,
    private vararg val validations: BaseValidationRule
) {

    fun validate(text: String?): Boolean {
        errorField.set(null)
        validations.forEach {
            if (!it.isValid(text)) {
                errorField.set(it.errorMessage)
                return false
            }
        }
        return true
    }
}