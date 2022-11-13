package com.example.ltechapp.data.datasource

import com.example.ltechapp.data.api.ApiService
import com.example.ltechapp.data.models.AuthBody
import com.example.ltechapp.domain.appstate.AppState
import com.example.ltechapp.domain.entities.AuthAnswer
import com.example.ltechapp.domain.entities.Mask
import com.example.ltechapp.domain.entities.News
import com.example.ltechapp.utils.LoginMapper.toAuthAnswer
import com.example.ltechapp.utils.LoginMapper.toMask
import com.example.ltechapp.utils.NewsMapper.toNews
import com.example.ltechapp.utils.EMPTY_DATA

class RemoteDataSourceImpl(private val api: ApiService) : RemoteDataSource {
    override suspend fun getMask(): AppState<Mask> {

        return try {
            val result = api.getMaskAsync().toMask()
            if (result.phoneMasks.isNotEmpty()) {
                AppState.Success(result)
            } else {
                AppState.Error(Exception(EMPTY_DATA))
            }
        } catch (err: Exception) {
            AppState.Error(err)
        }
    }

    override suspend fun authorize(phone: String, password: String): AppState<AuthAnswer> {
        return try {
            val result = api.authorise(AuthBody(phone, password)).toAuthAnswer()
            if (result.success) {
                AppState.Success(result)
            } else {
                AppState.Error(Exception(EMPTY_DATA))
            }
        } catch (err: Exception) {
            AppState.Error(err)
        }
    }

    override suspend fun getNews(): AppState<List<News>> {
        return try {
            val result = api.getNewsAsync().map { it.toNews() }
            if (result.isNotEmpty()) {
                AppState.Success(result)
            } else {
                AppState.Error(Exception(EMPTY_DATA))
            }
        } catch (err: Exception) {
            AppState.Error(err)
        }

    }

}