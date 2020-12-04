package com.farshatov.test.testapplication.data

import android.graphics.Bitmap

data class Contact(
    val id: String,

    val name: String,

    val phone: String,

    val photo: Bitmap?
)