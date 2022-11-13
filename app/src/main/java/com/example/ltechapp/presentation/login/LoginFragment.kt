package com.example.ltechapp.presentation.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.testingapp.MobileNavigationDirections
import com.example.testingapp.R
import com.example.testingapp.databinding.FragmentLoginBinding
import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.domain.entities.AuthAnswer
import com.example.ltechapp.domain.entities.Mask
import com.example.ltechapp.presentation.base.BaseViewBindingFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseViewBindingFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModel()

    companion object {
        const val NAME_SHARED_REPOSITORY = "Shared Repository"
    }

    var flag = 1
    var allMask = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val phoneEditText = viewBinding.phone
        val passwordEditText = viewBinding.passwordEt
        val loginButton = viewBinding.singIn
        viewModel.requestMask()
        viewBinding.statusPassword.setOnClickListener {
            changePasswordStatus()
        }
        viewBinding.clearPhone.setOnClickListener {
            viewBinding.phone.setText(
                viewBinding.phone.text.toString().substring(0, 1)
            )
        }

        if (!viewModel.isEmptyPassword() ) {
            phoneEditText.setText(viewModel.sharedPhone)
            passwordEditText.setText(viewModel.sharedPassword)
        }
        phoneEditText.addTextChangedListener()
        passwordEditText.addTextChangedListener()

        loginButton.setOnClickListener {
            viewModel.requestAuthorize(
                phoneEditText.text.toString().substring(1),
                passwordEditText.text.toString()
            )
        }


        setSignInButton(
            loginButton,
            phoneString = phoneEditText.text.toString().substring(1).trim(),
            passwordString = passwordEditText.text.toString().trim()
        )

        passwordEditText.addTextChangedListener(object : TextWatcher {
            @SuppressLint("UseCompatLoadingForColorStateLists")
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val phoneString = phoneEditText.text.toString().substring(1).trim()
                val passwordString = passwordEditText.text.toString().trim()
                setSignInButton(loginButton, phoneString, passwordString)
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun setSignInButton(
        loginButton: MaterialButton,
        phoneString: String,
        passwordString: String
    ) {
        loginButton.isEnabled = (phoneString.isNotEmpty() && passwordString.isNotEmpty())
        if (loginButton.isEnabled) {
            loginButton.backgroundTintList =
                context?.resources?.getColorStateList(R.color.light_blue)
        } else {
            loginButton.backgroundTintList =
                context?.resources?.getColorStateList(R.color.gray)
        }
    }

    private fun setState(state: AppState<*>) {
        when (state) {
            is AppState.Error -> {
                Log.d("SSSSTATE", "5")

                viewBinding.passwordEt.setBackgroundResource(R.drawable.error_edittext)
                Toast.makeText(context, state.error.toString(), Toast.LENGTH_LONG).show()
            }
            is AppState.Loading -> {

            }
            is AppState.Success -> {
                when (state.data) {
                    is AuthAnswer -> {
                        if (state.data.success) {
                            viewBinding.passwordEt.setBackgroundResource(R.drawable.round_corners_white)
                            saveData(
                                viewBinding.phone.text.toString(),
                                viewBinding.passwordEt.text.toString()
                            )
                            navigateToMainScreen()
                        } else {
                            viewBinding.passwordEt.setBackgroundResource(R.drawable.error_edittext)
                        }
                    }
                    else -> {
                        viewBinding.passwordEt.setBackgroundResource(R.drawable.error_edittext)
                    }
                }
            }
            else -> {}
        }
    }

    private fun navigateToMainScreen() {
        val navController = findNavController()
        val action = MobileNavigationDirections.actionGlobalMainFragment()
        navController.navigate(action)
    }

    private fun saveData(phoneEdit: String, passwordEdit: String) {
        viewModel.saveData(phoneEdit, passwordEdit)
    }


    private fun changePasswordStatus() {
        if (flag == 0) {
            viewBinding.statusPassword.setBackgroundResource(R.drawable.show_password)
            viewBinding.passwordEt.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            flag = 1
        } else {
            viewBinding.statusPassword.setBackgroundResource(R.drawable.hide_password)
            viewBinding.passwordEt.transformationMethod = PasswordTransformationMethod.getInstance()
            flag = 0
        }
    }


    override fun renderSuccess(result: AppState.Success<*>) {
        showLoading(false)
        when (val mask = result.data) {
            is Mask -> {
                allMask = mask.phoneMasks.substring(0, mask.phoneMasks.length - 1)
                val phoneMask = getMask(allMask)
                viewBinding.phone.setText(phoneMask)

            }
        }

    }

    private fun getMask(maskString: String): String {
        var i = 0
        var totalMask = ""
        while ((maskString[i] != 'Х')) {
            if ((maskString[i] == '(')) {
                return totalMask
            }
            totalMask += maskString[i]
            i++
        }
        return totalMask
    }


    override fun showLoading(isShow: Boolean) {
    }

    override fun showError(throwable: Throwable) {
        Snackbar.make(viewBinding.root, "Error", Snackbar.LENGTH_SHORT).show()
    }

    override fun initObservers() {
        viewModel.getMaskLiveData()
            .observe(viewLifecycleOwner)
            { res ->
                if (viewModel.isEmptyPhone() && viewModel.isEmptyPassword()
                ) {
                    renderData(result = res)
                }
            }
        viewModel.getAuthLiveData().observe(viewLifecycleOwner) {
            setState(it)
        }

    }
}