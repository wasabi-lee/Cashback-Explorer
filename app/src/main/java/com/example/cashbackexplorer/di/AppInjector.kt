package com.example.cashbackexplorer.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.cashbackexplorer.CashbackApp
import dagger.android.AndroidInjection

object AppInjector {
    lateinit var appComponent: AppComponent

    fun init(app: CashbackApp) {
        appComponent = DaggerAppComponent.builder().application(app)
            .appContext(app.applicationContext)
            .build()
        appComponent.inject(app)

        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }


        })
    }

    private fun handleActivity(activity: Activity) {
        AndroidInjection.inject(activity)
    }
}