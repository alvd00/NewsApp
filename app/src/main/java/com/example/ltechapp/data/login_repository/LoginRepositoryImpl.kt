package com.example.ltechapp.data.login_repository

import android.content.SharedPreferences
import com.example.ltechapp.data.datasource.RemoteDataSource
import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.domain.entities.AuthAnswer
import com.example.ltechapp.domain.entities.Mask

class LoginRepositoryImpl(
    private val dataSource: RemoteDataSource,
    private val sharedPreferences: SharedPreferences
) : ILoginRepository {

    override suspend fun getMask(): AppState<Mask> =
        dataSource.getMask()

    override suspend fun authorize(phone: String, password: String): AppState<AuthAnswer> =
        dataSource.authorize(phone, password)

    companion object {
        const val SHARED_PHONE_KEY = "Phone"
        const val SHARED_PASSWORD_KEY = "Password"

    }

    override var phone: String?
        get() = sharedPreferences.getString(
            SHARED_PHONE_KEY,
            ""
        )
        set(value) {
            sharedPreferences.edit()
                .putString(SHARED_PHONE_KEY, value)
                .apply()
        }


    override var password: String?
        get() = sharedPreferences.getString(SHARED_PASSWORD_KEY, "")
        set(value) {
            sharedPreferences.edit()
                .putString(SHARED_PASSWORD_KEY, value)
                .apply()
        }

}