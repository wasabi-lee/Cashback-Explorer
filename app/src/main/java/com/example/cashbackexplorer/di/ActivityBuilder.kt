package com.example.cashbackexplorer.di

import com.example.cashbackexplorer.AddNewActivity
import com.example.cashbackexplorer.ui.MainActivity
import com.example.cashbackexplorer.ui.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [AddNewActivityModule::class])
    abstract fun contributeAddNewActivity(): AddNewActivity
}