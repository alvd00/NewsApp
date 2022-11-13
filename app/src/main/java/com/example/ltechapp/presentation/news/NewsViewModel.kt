package com.example.ltechapp.presentation.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.domain.entities.News
import com.example.ltechapp.domain.usecases.getdata.GetNews
import com.example.ltechapp.presentation.base.BaseViewModel
import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds

class NewsViewModel(
    private val getNews: GetNews
) : BaseViewModel<AppState<*>>() {
    private val newsLiveData: MutableLiveData<AppState<*>>
            = MutableLiveData<AppState<*>>()
    private var news: List<News> = arrayListOf()

    fun getNewsLiveData() = newsLiveData

    fun sortNews(isDefault : Boolean) {
        viewModelScope.launch {
            if(!isDefault){
                news =
                    news.sortedBy{ it.sort }
                            newsLiveData.value = AppState.Success(news)
            }
            else {
                news =
                    news.sortedBy{ it.date }
                newsLiveData.value = AppState.Success(news)
            }
        }
    }

    fun requestNews(): Job =
        viewModelScope.launch {
            getNewsLiveData().postValue(AppState.Loading)
            val news = getNews.executeNews()
            if (news is AppState.Success<*>) {
                when (val news = news.data) {
                    is List<*> -> {
                        this@NewsViewModel.news = listOf()
                        this@NewsViewModel.news = (news as List<News>)
                        getNewsLiveData().postValue(
                            AppState.Success(this@NewsViewModel.news)
                        )
                    }
                }
            }
            if (news is AppState.Error) {
                getNewsLiveData().value = news
            }
        }

    fun timeoutUpdateNews() {
        viewModelScope.launch {
            while (isActive) {
                delay(DELAY)
                requestNews()
            }
        }
    }

    companion object {
        private val DELAY = 30.seconds
    }
}