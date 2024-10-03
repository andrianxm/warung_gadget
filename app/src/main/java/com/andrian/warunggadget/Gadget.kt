package com.andrian.warunggadget

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gadget(
    val name: String,
    val price: String,
    val photo: String,
    val dataSpecification: String,
    val detailSpecification: String,
    val linkShare: String
) : Parcelable