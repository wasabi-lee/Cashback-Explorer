package com.example.cashbackexplorer.api

import com.example.cashbackexplorer.model.User
import com.example.cashbackexplorer.model.Venue
import com.example.cashbackexplorer.model.VenueWrapper
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("users")
    fun addNewUser(@Body user: User
    ): Call<ResponseBody>


    @POST("login")
    fun refreshToken(@Body user: User
    ): Call<ResponseBody>


    @GET("venues")
    fun getVenues(@Query("city") city: String): Single<Response<VenueWrapper>>

}
