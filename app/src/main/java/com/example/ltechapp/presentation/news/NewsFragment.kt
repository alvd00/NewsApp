package com.example.ltechapp.presentation.news

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testingapp.R
import com.example.testingapp.databinding.FragmentNewsBinding
import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.domain.entities.News
import com.example.ltechapp.presentation.base.BaseViewBindingFragment
import com.example.ltechapp.presentation.news.adapter.NewsAdapter
import com.example.ltechapp.utils.hide
import com.example.ltechapp.utils.show
import com.example.ltechapp.utils.showSnakeBar
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsFragment : BaseViewBindingFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate),
    NewsAdapter.Delegator {

    private val viewModel: NewsViewModel by viewModel()
    private val adapter by lazy { NewsAdapter(this) }


    override fun initObservers() {

        viewModel.getNewsLiveData()
            .observe(viewLifecycleOwner) { res ->
                renderData(result = res)

            }
    }

    override fun renderSuccess(result: AppState.Success<*>) {
        showLoading(false)
        when (val news = result.data) {
            is List<*> -> {
                when (news.firstOrNull()) {
                    is News -> initNewsRcView(news as List<News>)

                }
            }
        }
    }

    private fun initNewsRcView(data: List<News>) = with(viewBinding) {
        recyclerNews.layoutManager = LinearLayoutManager(root.context)
        recyclerNews.adapter = adapter
        recyclerNews.itemAnimator?.changeDuration = 0
        adapter.setItems(data)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerSetting()
        getMoreNews()
        viewBinding.reloadNews.setOnClickListener {
            viewModel.requestNews()
        }
        viewBinding.sortTv.setOnClickListener {
            showBottomDialog()
        }
        viewModel.timeoutUpdateNews()
    }

    private fun initRecyclerSetting() {
        viewBinding.recyclerNews.also { recycler ->
            recycler.adapter = adapter
            recycler.setHasFixedSize(true)
        }
    }

    private fun showBottomDialog() {
        val dialogView = layoutInflater.inflate(R.layout.sorted_bottom_sheet_dialog, null)
        val bottomSheetDialog =
            context?.let { BottomSheetDialog(it, R.style.BottomSheetDialogTheme) }
        dialogView.setBackgroundResource(R.drawable.bottom_sheet_background)
        bottomSheetDialog?.setContentView(dialogView)
        bottomSheetDialog?.show()
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radio_group)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                -1 -> Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                R.id.sortByDateButton -> viewModel.sortNews(false)
                R.id.sortDefaultButton -> viewModel.sortNews(true)
            }
        }
        val closeDialog = dialogView.findViewById<ImageView>(R.id.close_dialog)
        closeDialog.setOnClickListener { bottomSheetDialog?.dismiss() }
    }


    override fun getMoreNews() {
        viewModel.requestNews()
    }

    override fun showLoading(isShow: Boolean) {
        if (isShow) {
            viewBinding.progress.show()
        } else {
            viewBinding.progress.hide()
        }
    }

    override fun showError(throwable: Throwable) {
        throwable.localizedMessage?.let { viewBinding.root.showSnakeBar(it) }
    }


    override fun onItemClick(news: News) {
        val action: NavDirections =
            NewsFragmentDirections.actionGlobalNewsDetailsFragment(
                news
            )
        findNavController().navigate(action)
    }
}
