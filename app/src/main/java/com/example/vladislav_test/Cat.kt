package com.example.vladislav_test

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cat(val image: Int, val text: String) : Parcelable
