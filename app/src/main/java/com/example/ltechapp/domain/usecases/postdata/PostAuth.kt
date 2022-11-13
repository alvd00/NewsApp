package com.example.ltechapp.domain.usecases.postdata

import com.example.ltechapp.data.login_repository.ILoginRepository
import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.domain.entities.AuthAnswer

class PostAuth(private val repository: ILoginRepository) {
    suspend fun sendDataToCheck( phone: String, password: String): AppState<AuthAnswer> =
        repository.authorize(password = password, phone = phone)

}