package com.example.ltechapp.data.api

import com.example.ltechapp.data.models.AuthAnswerResponse
import com.example.ltechapp.data.models.AuthBody
import com.example.ltechapp.data.models.MaskResponse
import com.example.ltechapp.data.models.NewsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("phone_masks")
    suspend fun getMaskAsync(): MaskResponse

    @GET("posts")
    suspend fun getNewsAsync(): List<NewsResponse>

    @POST("auth")
    suspend fun authorise(
        @Body body: AuthBody,
    ): AuthAnswerResponse

}