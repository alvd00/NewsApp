package com.example.ltechapp.presentation.news_details

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.testingapp.R
import com.example.testingapp.databinding.NewsDetailsFragmentBinding
import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.domain.entities.News
import com.example.ltechapp.presentation.base.BaseViewBindingFragment
import com.example.ltechapp.utils.NEWS_URL
import com.example.ltechapp.utils.showSnakeBar

class NewsDetailsFragment :
    BaseViewBindingFragment<NewsDetailsFragmentBinding>(NewsDetailsFragmentBinding::inflate),
    NewsDetailsClickListener {
    private val args: NewsDetailsFragmentArgs by navArgs()

    companion object {
        private const val CORNER_RADIUS = 14
        private const val DELAY = 800
    }

    override fun initObservers() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderData(args.newsArgs)
        viewBinding.back.setOnClickListener { findNavController().popBackStack() }
        viewBinding.shareNews.setOnClickListener { openShareApp() }
    }


    override fun openShareApp() {
        val message = args.newsArgs.text
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun renderSuccess(result: AppState.Success<*>) {
        showLoading(false)
        renderData(args.newsArgs)

    }

    private fun renderData(news: News) {
        with(viewBinding)
        {
            dateTv.text = news.date
            title.text = news.title
            description.text = news.text
            Glide.with(poster)
                .load(NEWS_URL.plus(news.image))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(CORNER_RADIUS)))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transition(DrawableTransitionOptions().crossFade(DELAY))
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(poster)
        }
    }


    override fun showLoading(isShow: Boolean) {
    }


    override fun showError(throwable: Throwable) {
        viewBinding.root.showSnakeBar(throwable.localizedMessage)
    }

}