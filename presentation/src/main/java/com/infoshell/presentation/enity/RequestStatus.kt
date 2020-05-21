package com.infoshell.presentation.enity

import androidx.databinding.ObservableBoolean

class RequestStatus {
    var isLoaded = ObservableBoolean(false)
    var isProcessing = ObservableBoolean()
}