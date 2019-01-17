package com.example.cashbackexplorer.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.cashbackexplorer.R
import com.example.cashbackexplorer.utils.SharedPrefHelper
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.NullPointerException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPreference(app: Application): SharedPreferences {
        return app.getSharedPreferences(app.getString(R.string.pref_open_key), Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    @Named("prefOpenKey")
    fun providesPreferenceOpenKey(context: Context): String {
        return context.getString(R.string.pref_open_key)
    }

    @Singleton
    @Provides
    @Named("prefAuthKey")
    fun providesPreferenceAuthKey(context: Context): String {
        return context.getString(R.string.pref_auth_key)
    }

    @Singleton
    @Provides
    @Named("prefNameKey")
    fun providesPreferenceNameKey(context: Context): String {
        return context.getString(R.string.pref_name_key)
    }

    @Singleton
    @Provides
    @Named("prefEmailKey")
    fun providesPreferenceEmailKey(context: Context): String {
        return context.getString(R.string.pref_email_key)
    }



}