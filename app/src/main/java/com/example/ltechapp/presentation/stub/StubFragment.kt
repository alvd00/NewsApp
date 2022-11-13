package com.example.ltechapp.presentation.stub

import android.os.Bundle
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.testingapp.databinding.FragmentStubBinding
import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.presentation.base.BaseViewBindingFragment
import com.example.ltechapp.presentation.stub.StubClickListener

class StubFragment :
    BaseViewBindingFragment<FragmentStubBinding>(FragmentStubBinding::inflate),
    StubClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.toNews.setOnClickListener { navigateToNews() }
        viewBinding.singOut.setOnClickListener { exit() }

    }


    override fun renderSuccess(result: AppState.Success<*>) {

    }

    override fun showLoading(isShow: Boolean) {
    }

    override fun showError(throwable: Throwable) {
    }

    override fun initObservers() {
    }

    override fun navigateToNews() {
        val action: NavDirections =
            StubFragmentDirections.actionGlobalMainFragment()
        findNavController().navigate(action)

    }

    override fun exit() {
        val action: NavDirections =
            StubFragmentDirections.actionToLogin()
        findNavController().navigate(action)
    }

}