package com.example.ltechapp.domain.usecases.getdata

import com.example.ltechapp.data.news_repository.INewsRepository
import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.domain.entities.News

class GetNews(private val repository: INewsRepository)  {
    suspend fun executeNews() : AppState<List<News>> {
        return repository.getNews()
    }

}