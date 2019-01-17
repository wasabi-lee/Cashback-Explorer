package com.example.cashbackexplorer

import android.app.Activity
import android.app.Application
import com.example.cashbackexplorer.di.AppComponent
import com.example.cashbackexplorer.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class CashbackApp: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
    }
}