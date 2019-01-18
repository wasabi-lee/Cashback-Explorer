package com.example.cashbackexplorer.api

import com.example.cashbackexplorer.model.Venue
import okhttp3.Response

interface NetworkCallback {

    interface SignUpCallback {
        fun onSuccessful(authToken: String)
        fun onError(errorType: ResponseValidator.ErrorType)
    }

    interface OnReceiveVenuesCallback {
        fun onReceiveVenues(venues: List<Venue>)
        fun onRefreshNeeded()
        fun onVenueCallbackError(throwable: Throwable?)
    }

    interface RefreshCallback {
        fun onRefreshed(newToken: String)
        fun onRefreshError(throwable: Throwable?)
    }
}