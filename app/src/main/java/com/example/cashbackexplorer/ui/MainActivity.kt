package com.example.cashbackexplorer.ui

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.cashbackexplorer.AddNewActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.example.cashbackexplorer.R
import com.example.cashbackexplorer.R.layout.bottom_sheet
import com.example.cashbackexplorer.databinding.ActivityMainBinding
import com.example.cashbackexplorer.model.Venue
import com.example.cashbackexplorer.ui.viewmodel.MainViewModel
import com.example.cashbackexplorer.utils.PermissionHelper
import com.example.cashbackexplorer.utils.SharedPrefHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.ui.IconGenerator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private lateinit var lastLocation: Location

    private var mMap: GoogleMap? = null

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)
        binding.viewmodel = mainViewModel

        checkCredential()
        setupObservers()
        initMap()
        setupBottomSheet()

    }


    private fun initMap() {
        val mapFragment =
            ((supportFragmentManager.findFragmentById(R.id.main_map_frag)) as SupportMapFragment)
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap?.setOnMarkerClickListener(this)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setupMap()
        getUserLocation()
        mainViewModel.requestVenues()
    }


    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from<ConstraintLayout>(bottom_sheet_parent)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }


    private fun setupMap() {
        if (!PermissionHelper.isLocationPermissionGranted(this)) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PermissionHelper.RC_LOCATION_PERMISSION
            )
        }

        mMap?.uiSettings?.isZoomControlsEnabled = true

    }


    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        if (PermissionHelper.isLocationPermissionGranted(this)) {
            mMap?.isMyLocationEnabled = true
            mFusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    lastLocation = it
                    val currentLatLng = LatLng(it.latitude, it.longitude)
                    mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> getUserLocation()
        }
    }


    private fun checkCredential() {
        if (sharedPrefHelper.getAuthToken() == null) {
            toLoginActivity()
        }
    }


    private fun setupObservers() {
        mainViewModel.venueList.observe(this, Observer {
            venues ->
            venues?.forEach {
                venue -> drawMarker(venue)
            }
        })

        mainViewModel.toastText.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        mainViewModel.expandBottomSheet.observe(this, Observer {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        })

        mainViewModel.toLoginActivity.observe(this, Observer {
            toLoginActivity()
        })
    }


    private fun toLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun toAddNewActivity() {
        val intent = Intent(this, AddNewActivity::class.java)
        startActivity(intent)
    }


    private fun drawMarker(venue: Venue) {
        val ig = IconGenerator(this)
        val markerOptions = MarkerOptions()
            .position(LatLng(venue.lat.toDouble(), venue.long.toDouble()))
            .icon(BitmapDescriptorFactory.fromBitmap(ig.makeIcon("${venue.cashback}%")))
            .anchor(ig.anchorU, ig.anchorV)

        val marker = mMap?.addMarker(markerOptions)
        marker?.tag = venue
    }


    override fun onMarkerClick(marker: Marker?): Boolean {
        mainViewModel.displaySelectedVenue((marker?.tag) as Venue)
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_add)
            toAddNewActivity()
        return super.onOptionsItemSelected(item)
    }

}
