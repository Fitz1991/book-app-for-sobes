package com.infoshell.presentation.enity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DisplayCategory(var id: String, var name: String = "", var icon: String = "")  : Parcelable