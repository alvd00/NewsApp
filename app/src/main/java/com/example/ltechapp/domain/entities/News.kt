package com.example.ltechapp.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val title: String,
    val text: String,
    val image: String,
    val sort: Int,
    val date: String
) : Parcelable
