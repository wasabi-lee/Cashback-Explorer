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



    /** Saves AuthToken in SharedPreferences and returns true if the it was successfully saved
     * @param authToken Authentication token to save
     * @return True if the operation was successful, false otherwise
      */
    fun saveAuthToken(authToken: String): Boolean {
        val editor = sharedPref.edit()
        editor.putString(authKey, authToken)
        return editor.commit()
    }

}