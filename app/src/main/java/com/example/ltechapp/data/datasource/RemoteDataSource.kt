package com.example.ltechapp.data.datasource

import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.domain.entities.AuthAnswer
import com.example.ltechapp.domain.entities.Mask
import com.example.ltechapp.domain.entities.News


interface RemoteDataSource {
    suspend fun getMask(): AppState<Mask>
    suspend fun authorize(phone: String, password: String): AppState<AuthAnswer>
    suspend fun getNews(): AppState<List<News>>
}