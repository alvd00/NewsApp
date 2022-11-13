package com.example.ltechapp.data.news_repository

import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.domain.entities.News

interface INewsRepository {
    suspend fun getNews(): AppState<List<News>>
}