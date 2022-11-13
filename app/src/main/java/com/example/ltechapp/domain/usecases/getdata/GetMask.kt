package com.example.ltechapp.domain.usecases.getdata

import com.example.ltechapp.data.login_repository.ILoginRepository
import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.domain.entities.Mask

class GetMask(private val repository: ILoginRepository) {
    suspend fun execute(): AppState<Mask> =
        repository.getMask()
}