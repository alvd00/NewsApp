package com.example.ltechapp.data.models

import com.google.gson.annotations.SerializedName

data class MaskResponse (
    @SerializedName("phoneMask")
    val phoneMasks: String
)