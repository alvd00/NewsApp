package com.example.ltechapp.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("sort")
    val sort: Int,
    @SerializedName("date")
    val date: String
) : Parcelable