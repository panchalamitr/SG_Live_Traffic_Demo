package com.panchalamitr.sglivetraffic.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.haroldadmin.cnradapter.NetworkResponse
import com.panchalamitr.sglivetraffic.R
import com.panchalamitr.sglivetraffic.adapter.CustomInfoWindowAdapter
import com.panchalamitr.sglivetraffic.common.Constants
import com.panchalamitr.sglivetraffic.databinding.ActivityMapsBinding
import com.panchalamitr.sglivetraffic.respository.DefaultTrafficImagesRepository
import com.panchalamitr.sglivetraffic.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    @Inject
    lateinit var defaultTrafficImagesRepository: DefaultTrafficImagesRepository

    private lateinit var activityMapBinding: ActivityMapsBinding

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var mapsFragment: SupportMapFragment

    /**
     * Helps to just update image Url, in marker instead of
     * removing and adding marker in Google Map.
     * Key -> CameraId, Value -> Marker
     */
    private val cameraHashMap = HashMap<String, Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMapBinding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(activityMapBinding.root)

        mapsFragment = supportFragmentManager.findFragmentById(R.id.mapFragment)!! as SupportMapFragment
        mapsFragment.getMapAsync(this)

        observeErrorResponse()

        Timber.d("ActivityState: onCreate")
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.syncStart()
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.syncStop()
    }

    private fun observeTrafficCameras(googleMap: GoogleMap) {
        mainViewModel.observeTrafficCameras().observe(this, Observer { it ->
            showSyncStarMessage()
            it.items[0].cameras.forEach {
                val lat = it.location.latitude
                val lng = it.location.longitude
                var marker: Marker
                if (cameraHashMap.containsKey(it.cameraId)) {
                    marker = cameraHashMap[it.cameraId]!!
                    Timber.d("Update")
                } else {
                    marker = googleMap.addMarker(MarkerOptions().position(LatLng(lat, lng)))
                    cameraHashMap[it.cameraId] = marker
                    Timber.d("Add")
                }
                marker.tag = it.image
            }
        })
    }

    private fun observeErrorResponse() {
        mainViewModel.observeErrorResponse().observe(this, Observer {
            when(it){
                is NetworkResponse.ServerError<*> -> {
                    Toast.makeText(this, it.body.toString(), Toast.LENGTH_LONG).show()
                }

                /** When no internet connection **/
                is NetworkResponse.NetworkError -> {
                    Toast.makeText(this,it.error.message, Toast.LENGTH_LONG).show()
                }

                /** When no internet connection **/
                is NetworkResponse.UnknownError -> {
                    Toast.makeText(this,it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun showSyncStarMessage(){
        Snackbar.make(activityMapBinding.root, getString(R.string.snackbar_msg), Snackbar.LENGTH_LONG).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {

        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isZoomGesturesEnabled = true

        activityMapBinding.progressBar.visibility = View.GONE

        /** We are moving camera position to Singapore **/
        val cameraPosition = CameraUpdateFactory.newLatLngZoom(
            LatLng(Constants.SG_LAT, Constants.SG_LNG), 12.0f
        )
        googleMap.animateCamera(cameraPosition)
        /**
         * Works on physical device only, tried in emulator, tap is not working
         * in emulator
         **/
        googleMap.setInfoWindowAdapter(CustomInfoWindowAdapter(layoutInflater))

        /** start observing Traffic Camera after map is ready **/
        observeTrafficCameras(googleMap)
    }

}
