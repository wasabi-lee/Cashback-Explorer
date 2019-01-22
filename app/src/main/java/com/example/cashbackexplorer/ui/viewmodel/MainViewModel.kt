package com.example.cashbackexplorer.ui.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.example.cashbackexplorer.api.NetworkCallback
import com.example.cashbackexplorer.api.NetworkRepository
import com.example.cashbackexplorer.model.Venue
import com.example.cashbackexplorer.utils.SharedPrefHelper
import com.example.cashbackexplorer.utils.SingleLiveEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val app: Application,
    val repo: NetworkRepository,
    val sharedPrefHelper: SharedPrefHelper
) : AndroidViewModel(app), NetworkCallback.OnReceiveVenuesCallback,
NetworkCallback.RefreshCallback{

    private val TAG = LoginViewModel::class.java.simpleName

    val toastText = SingleLiveEvent<String>()

    val venueList = SingleLiveEvent<List<Venue>>()

    val loading = ObservableBoolean(false)

    val selectedVenue = ObservableField<Venue>()

    val expandBottomSheet = SingleLiveEvent<Boolean>()

    val toLoginActivity = SingleLiveEvent<Void>()

    fun requestVenues() {
        if (sharedPrefHelper.getAuthToken().isNullOrEmpty()) {
            // Auth token doesn't exist! Make user login again
            toLoginActivity.value = null
        } else {
            repo.requestVenues(sharedPrefHelper.getAuthToken()!!, "New York", this)
        }
    }


    override fun onReceiveVenues(venues: List<Venue>) {
        venueList.value = venues
    }


    override fun onRefreshNeeded() {
        val token = sharedPrefHelper.getAuthToken()
        val name = sharedPrefHelper.getName()
        val email = sharedPrefHelper.getName()

        if (token?.isNullOrEmpty() == true ||
            name?.isNullOrEmpty() == true ||
            email?.isNullOrEmpty() == true) {
            // invoking login activity
            toLoginActivity.value = null
            return
        }

        repo.refreshToken(token,name, email, this)
    }


    override fun onVenueCallbackError(throwable: Throwable?) {
        throwable?.printStackTrace()
        toastText.value = throwable?.message
    }


    override fun onRefreshed(newToken: String) {
        sharedPrefHelper.saveAuthToken(newToken)
        repo.requestVenues(sharedPrefHelper.getAuthToken()!!, "New York", this)
    }


    override fun onRefreshError(throwable: Throwable?) {
        throwable?.printStackTrace()
        toastText.value = throwable?.message

        // invoking login activity
        toLoginActivity.value = null
    }


    // Display the selected marker detail to the UI
    fun displaySelectedVenue(venue: Venue) {

        // Setting new data to display
        selectedVenue.set(venue)
        selectedVenue.notifyChange()

        // Expanding bottom sheet
        expandBottomSheet.value = true
    }
}