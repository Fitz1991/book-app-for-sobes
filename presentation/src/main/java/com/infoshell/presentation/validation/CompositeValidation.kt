package com.infoshell.presentation.validation

const val EMAIL_VALIDATION = "EMAIL_VALIDATION"
const val PASSWORD_VALIDATION = "PASSWORD_VALIDATION"
const val FIRST_SECOND_NAME_VALIDATION = "FIRST_SECOND_NAME_VALIDATION"
const val PHONE_VALIDATION = "PHONE_VALIDATION"

class CompositeValidation {

    private val validations = hashMapOf<String, FieldValidation>()

    fun registerValidation(key: String, fieldValidation: FieldValidation): CompositeValidation {
        validations[key] = fieldValidation
        return this
    }

    fun getValidation(key: String): FieldValidation {
        return validations[key] ?: throw IllegalStateException("Validation has not been registered")
    }

    fun validateEmail(text: String?) = validate(EMAIL_VALIDATION, text)

    fun validatePassword(text: String?) = validate(PASSWORD_VALIDATION, text)

    fun validate(key: String, text: String?) = getValidation(key).validate(text)

    fun clear() {
        validations.clear()
    }
}