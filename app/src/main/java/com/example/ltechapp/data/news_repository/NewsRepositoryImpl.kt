package com.example.ltechapp.data.news_repository

import com.example.ltechapp.data.datasource.RemoteDataSource
import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.domain.entities.News

class NewsRepositoryImpl(private val dataSource: RemoteDataSource): INewsRepository {
    override suspend fun getNews(): AppState<List<News>> =
        dataSource.getNews()
}