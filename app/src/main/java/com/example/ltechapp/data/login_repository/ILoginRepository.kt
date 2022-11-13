package com.example.ltechapp.data.login_repository

import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.domain.entities.AuthAnswer
import com.example.ltechapp.domain.entities.Mask

interface ILoginRepository {
    suspend fun getMask(): AppState<Mask>
    suspend fun authorize(phone: String, password: String): AppState<AuthAnswer>
    var phone: String?
    var password: String?

}