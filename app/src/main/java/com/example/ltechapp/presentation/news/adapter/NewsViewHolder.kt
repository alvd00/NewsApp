package com.example.ltechapp.presentation.news.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.testingapp.R
import com.example.testingapp.databinding.NewsDetailsBinding
import com.example.ltechapp.domain.entities.News
import com.example.ltechapp.utils.NEWS_URL


class NewsViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    companion object {
        private const val CORNER_RADIUS = 14
        private const val DELAY = 800
    }

    private val viewBinding: NewsDetailsBinding by viewBinding()

    fun bind(
        news: News,
        delegate: NewsAdapter.Delegator?
    ) {
        with(viewBinding) {
            title.text = news.title
            description.text = news.text
            newsDate.text = news.date
            Glide.with(poster)
                .load(NEWS_URL.plus(news.image))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(CORNER_RADIUS)))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transition(DrawableTransitionOptions().crossFade(DELAY))
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(poster)
            root.setOnClickListener{
                delegate?.onItemClick(news)
            }
        }
    }


}