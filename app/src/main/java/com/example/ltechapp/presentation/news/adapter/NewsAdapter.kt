package com.example.ltechapp.presentation.news.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testingapp.R
import com.example.ltechapp.domain.entities.News


class NewsAdapter(private val delegate: Delegator?) :
    RecyclerView.Adapter<NewsViewHolder?>() {

    interface Delegator {
        fun onItemClick(news : News)
        fun getMoreNews()
    }

    private var data = listOf<News>()

    fun setItems(newList: List<News>) {
        val result = DiffUtil.calculateDiff(
            DiffUtilCallback(
                data,
                newList
            )
        )
        result.dispatchUpdatesTo(this)
        data = listOf()
        data=(newList)
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.news_details,
                parent,
                false
            )
        )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) =
        holder.bind(data[position], delegate)

    inner class DiffUtilCallback(
        private var oldItems: List<News>,
        private var newItems: List<News>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}