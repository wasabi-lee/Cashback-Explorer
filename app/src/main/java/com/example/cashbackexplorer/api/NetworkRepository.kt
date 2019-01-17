package com.example.cashbackexplorer.api

import android.annotation.SuppressLint
import com.example.cashbackexplorer.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(val retrofitFactory: RetrofitFactory) {

    @SuppressLint("CheckResult")
    fun signUpUser(name: String, email: String, callback: NetworkCallback.SignUpCallback) {
        retrofitFactory.createRetrofit()
            .create(ApiService::class.java)
            .addNewUser(User(name, email))
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    when {
                        response.isSuccessful -> {
                            val token = response.headers().get("token")
                            if (token == null) {
                                callback.onError(Throwable("Authentication error"))
                                return
                            }
                            callback.onSuccessful(token)
                        }
                        response.code() == 401 -> callback.onError(Throwable("Unauthorized"))
                        else -> callback.onError(Throwable("Unexpected error"))
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                    callback.onError(Throwable("Unexpected Error"))
                }
            })
    }


    @SuppressLint("CheckResult")
    fun requestVenues(authToken: String, city: String, callback: NetworkCallback.OnReceiveVenuesCallback) {
        retrofitFactory.createAuthRetrofit(authToken)
            .create(ApiService::class.java)
            .getVenues(city)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { response ->
                    if (response.isSuccessful) {
                        if (response.body() == null || response.body()?.venues.isNullOrEmpty()) {
                            callback.onVenueCallbackError(Throwable("No venues found"))
                        } else {
                            callback.onReceiveVenues(response.body()!!.venues)
                        }
                    } else if (response.code() == 401) {
                        // Refresh Token
                        callback.onRefreshNeeded()
                    }
                },
                onError = callback::onVenueCallbackError
            )
    }


    fun refreshToken(authToken: String, name: String, email: String, callback: NetworkCallback.RefreshCallback) {
        retrofitFactory.createAuthRetrofit(authToken)
            .create(ApiService::class.java)
            .refreshToken(User(name, email))
            .enqueue(object: Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    when {
                        response.isSuccessful -> {
                            val token = response.headers().get("token")
                            if (token == null) {
                                callback.onRefreshError(Throwable("Authentication error"))
                                return
                            }
                            callback.onRefreshed(token)
                        }
                        response.code() == 401 -> callback.onRefreshError(Throwable("Unauthorized"))
                        else -> callback.onRefreshError(Throwable("Unexpected error"))
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callback.onRefreshError(t)
                }
            })
    }

}