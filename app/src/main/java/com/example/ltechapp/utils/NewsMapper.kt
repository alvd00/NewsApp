package com.example.ltechapp.utils

import com.example.ltechapp.data.models.NewsResponse
import com.example.ltechapp.domain.entities.News

object NewsMapper {
    fun NewsResponse.toNews(): News =
        News(
            title = this.title,
            text = this.text,
            image = this.image,
            sort = this.sort,
            date = this.date
        )
}