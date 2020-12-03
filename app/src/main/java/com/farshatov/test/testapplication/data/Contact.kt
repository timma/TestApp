package com.farshatov.test.testapplication.data

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable


data class Contact(
    val id: String,

    val name: String,

    val phone: String,

    val photo: Bitmap?
)