package com.example.cashbackexplorer.api

import com.example.cashbackexplorer.utils.SharedPrefHelper
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.NullPointerException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


class RetrofitFactory @Inject constructor() {


    fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor())
            .build()
    }


    fun createOkHttpClientWithAuthHeader(authToken: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getLoggingInterceptor())
            .addInterceptor { chain: Interceptor.Chain ->
                val request: Request = chain.request()
                    .newBuilder()
                    .addHeader("token", authToken)
                    .build()
                return@addInterceptor chain.proceed(request)
            }
            .build()
    }


    fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://cashback-explorer-api.herokuapp.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()
    }


    fun createAuthRetrofit(authToken: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://cashback-explorer-api.herokuapp.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClientWithAuthHeader(authToken))
            .build()
    }
}
