package com.example.ltechapp.di

import android.content.Context
import android.content.SharedPreferences
import com.example.ltechapp.data.api.ApiInterceptor
import com.example.ltechapp.data.api.ApiService
import com.example.ltechapp.data.datasource.RemoteDataSource
import com.example.ltechapp.data.datasource.RemoteDataSourceImpl
import com.example.ltechapp.data.login_repository.ILoginRepository
import com.example.ltechapp.data.login_repository.LoginRepositoryImpl
import com.example.ltechapp.data.news_repository.INewsRepository
import com.example.ltechapp.data.news_repository.NewsRepositoryImpl
import com.example.ltechapp.domain.usecases.getdata.GetMask
import com.example.ltechapp.domain.usecases.getdata.GetNews
import com.example.ltechapp.domain.usecases.postdata.PostAuth
import com.example.ltechapp.domain.usecases.shared_preferences.SharedPref
import com.example.ltechapp.presentation.login.LoginFragment.Companion.NAME_SHARED_REPOSITORY
import com.example.ltechapp.presentation.login.LoginViewModel
import com.example.ltechapp.presentation.news.NewsViewModel
import com.example.ltechapp.utils.TESTINGAPP_BASE_URL
import com.example.ltechapp.utils.TIME_OUT_CONNECT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val sharedPrefModule = module {

    single<SharedPreferences> {
        androidContext().getSharedPreferences(NAME_SHARED_REPOSITORY, Context.MODE_PRIVATE)
    }
}

val apiModule = module {
    single<Interceptor> {
        ApiInterceptor()
    }

    single<Gson> {
        GsonBuilder()
            .create()
    }

    single<ApiService> {
        Retrofit.Builder()

            .baseUrl(TESTINGAPP_BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                        HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
            .create(ApiService::class.java)
    }
}

/**
 * Модуль новостей
 */
val newsScreenModule = module {


    single<INewsRepository>() {
        NewsRepositoryImpl(
            dataSource = get()
        )
    }

    single<RemoteDataSource> {
        RemoteDataSourceImpl(api = get())
    }
    viewModel() {
        NewsViewModel(
            getNews = get()
        )
    }

}


/**
 * Модуль авторизации
 */
val loginScreenModule = module {

    single<ILoginRepository>() {
        LoginRepositoryImpl(
            dataSource = get(),
            sharedPreferences = get()
        )
    }

    viewModel() {
        LoginViewModel(
            getMask = get(),
            postAuth = get(),
            sharedPreferences = get()
        )
    }
}

val useCasesModule = module {
    factory {
        GetMask(repository = get())
    }
    factory {
        PostAuth(repository = get())
    }
    factory {
        GetNews(repository = get())
    }
    factory {
        SharedPref(repository = get())
    }
}