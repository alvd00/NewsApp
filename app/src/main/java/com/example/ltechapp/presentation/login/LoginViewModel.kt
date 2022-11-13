package com.example.ltechapp.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.domain.entities.AuthAnswer
import com.example.ltechapp.domain.entities.Mask
import com.example.ltechapp.domain.usecases.getdata.GetMask
import com.example.ltechapp.domain.usecases.postdata.PostAuth
import com.example.ltechapp.domain.usecases.shared_preferences.SharedPref
import com.example.ltechapp.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(

    private val getMask: GetMask,
    private val postAuth: PostAuth,
    private val sharedPreferences : SharedPref
) : BaseViewModel<AppState<*>>() {
    private val maskLiveData: MutableLiveData<AppState<Mask>>
            = MutableLiveData<AppState<Mask>>()
    private val authLiveData: MutableLiveData<AppState<AuthAnswer>>
            = MutableLiveData<AppState<AuthAnswer>>()

    var phoneMask: String = ""
    var isSuccessAuth = false
    val sharedPhone = sharedPreferences.sharedPhone
    val sharedPassword = sharedPreferences.sharedPassword
    fun getMaskLiveData() = maskLiveData
    fun getAuthLiveData() = authLiveData

    fun requestMask(): Job =
        viewModelScope.launch {
            getMaskLiveData().postValue(AppState.Loading)
            val maskRemote = getMask.execute()
            if (maskRemote is AppState.Success) {
                when (val mask = maskRemote.data) {
                    is Mask -> {
                        phoneMask = mask.phoneMasks
                        getMaskLiveData().postValue(
                            AppState.Success<Mask>(mask.copy(phoneMasks = phoneMask))
                        )
                    }
                    else -> {
                    }
                }
            }
        }

    fun requestAuthorize(phone: String, password: String): Job =
        viewModelScope.launch {
            getAuthLiveData().postValue(AppState.Loading)
            val authorize = postAuth.sendDataToCheck(phone, password)
            if (authorize is AppState.Success) {
                when (val auth = authorize.data) {
                    is AuthAnswer -> {
                        isSuccessAuth = auth.success
                        getAuthLiveData().postValue(
                            AppState.Success(auth.copy(success = isSuccessAuth))
                        )
                    }
                    else -> {

                    }
                }
            }
            else {          getAuthLiveData().postValue(
                AppState.Error(error = Throwable())
            )}
        }

    fun isEmptyPhone() : Boolean
    {
        return sharedPreferences.sharedPhone.toString().substring(1).isEmpty()
    }

    fun isEmptyPassword() : Boolean
    {
        return sharedPreferences.sharedPassword.toString().isEmpty()
    }

    fun saveData(phoneEdit: String, passwordEdit: String)
    {
        sharedPreferences.apply {
            sharedPhone = phoneEdit
            sharedPassword = passwordEdit
        }
    }

}