package com.infoshell.domain.entity

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}