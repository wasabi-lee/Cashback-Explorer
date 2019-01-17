package com.example.cashbackexplorer.utils

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

object PermissionHelper {

    const val RC_LOCATION_PERMISSION = 1

    fun isLocationPermissionGranted(context: Context): Boolean {
        return (ActivityCompat.checkSelfPermission(
            context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
    }

}