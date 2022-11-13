package com.example.ltechapp.presentation.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.ltechapp.domain.appstate.AppState


abstract class BaseViewBindingFragment<VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {
    private var binding: VB? = null
    protected val viewBinding: VB get() = binding!!

    protected abstract fun renderSuccess(result: AppState.Success<*>)
    protected abstract fun showLoading(isShow: Boolean)
    protected abstract fun showError(throwable: Throwable)
    protected abstract fun initObservers()

    protected fun renderData(result: AppState<*>) {
        when (result) {
            is AppState.Error -> {
                showLoading(false)
                showError(result.error)

            }
            is AppState.Loading -> {
                showLoading(true)
            }
            is AppState.Success<*> -> {
                renderSuccess(result)

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, container, false)
        initObservers()
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}