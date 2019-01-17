package com.example.cashbackexplorer.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.cashbackexplorer.R
import javax.inject.Inject
import javax.inject.Named

class SharedPrefHelper @Inject constructor(val sharedPref: SharedPreferences,
                                           @Named("prefAuthKey") val authKey: String,
                                           @Named("prefNameKey") val nameKey: String,
                                           @Named("prefEmailKey") val emailKey: String) {



    fun getAuthToken(): String? {
        return sharedPref.getString(authKey, null)
    }


    fun getName(): String? {
        return sharedPref.getString(nameKey, null)
    }

    fun getEmail(): String? {
        return sharedPref.getString(emailKey, null)
    }



    fun saveAuthToken(authToken: String): Boolean {
        val editor = sharedPref.edit()
        editor.putString(authKey, authToken)
        return editor.commit()
    }

    fun saveUsername(name: String): Boolean {
        val editor = sharedPref.edit()
        editor.putString(nameKey, name)
        return editor.commit()
    }

    fun saveEmail(email: String): Boolean {
        val editor = sharedPref.edit()
        editor.putString(emailKey, email)
        return editor.commit()
    }

}