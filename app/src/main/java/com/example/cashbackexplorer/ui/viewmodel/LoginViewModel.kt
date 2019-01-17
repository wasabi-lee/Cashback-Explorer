package com.example.cashbackexplorer.ui.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import com.example.cashbackexplorer.api.NetworkCallback
import com.example.cashbackexplorer.api.NetworkRepository
import com.example.cashbackexplorer.utils.SharedPrefHelper
import com.example.cashbackexplorer.utils.SingleLiveEvent
import javax.inject.Inject

class LoginViewModel @Inject constructor(val app: Application, val repo: NetworkRepository,
                                         val sharedPrefHelper: SharedPrefHelper)
    : AndroidViewModel(app), NetworkCallback.SignUpCallback {

    private val TAG = LoginViewModel::class.java.simpleName

    val loading = ObservableBoolean(false)

    val name = ObservableField<String>("")

    val email = ObservableField<String>("")

    val nameError = SingleLiveEvent<String>()

    val emailError = SingleLiveEvent<String>()

    val toastText = SingleLiveEvent<String>()

    val toMainActivity = SingleLiveEvent<Void>()

    fun proceedSignUp() {
        // Valid input. Proceed the sign in.
        val nameVal = name.get()
        val emailVal = email.get()
        if (!nameVal.isNullOrEmpty() && !emailVal.isNullOrEmpty()) {
            loading.set(true)
            repo.signUpUser(nameVal, emailVal, this)
        }

        // Name field is empty
        if (name.get().isNullOrEmpty())
            nameError.value = "Please type your name"
        // Email field is empty
        if (email.get().isNullOrEmpty())
            emailError.value = "Please type your email"
    }


    override fun onSuccessful(authToken: String) {
        // Preserving token
        sharedPrefHelper.saveAuthToken(authToken)

        // At this point name and email are guaranteed to be non null
        sharedPrefHelper.saveUsername(name.get()!!)
        sharedPrefHelper.saveEmail(email.get()!!)

        // Taking down progress bar
        loading.set(false)

        // Toast message
        toastText.value = "Welcome!"

        // Invoke MainActivity
        toMainActivity.value = null
    }

    override fun onError(throwable: Throwable?) {
        throwable?.printStackTrace()
        toastText.value = throwable?.message
    }


}