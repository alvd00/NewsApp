package com.example.ltechapp.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ltechapp.domain.appstate.AppState
import kotlinx.coroutines.*

abstract class BaseViewModel<T : AppState<*>>(
) : ViewModel() {


    override fun onCleared() {
        super.onCleared()
        viewModelScope
            .coroutineContext
            .cancel()
    }
}