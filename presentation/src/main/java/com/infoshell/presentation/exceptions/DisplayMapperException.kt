package com.infoshell.presentation.exceptions

import java.lang.Exception

class DisplayMapperException : Exception {

    constructor(message: String?) : super(message)

    constructor(cause: Throwable?) : super(cause)
}