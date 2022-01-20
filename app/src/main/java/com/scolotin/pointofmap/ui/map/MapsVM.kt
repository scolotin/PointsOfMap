package com.scolotin.pointofmap.ui.map

import android.annotation.SuppressLint
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.scolotin.pointofmap.ui.common.SharedViewModel

class MapsVM(
    private val map: GoogleMap,
    private val geocoder: Geocoder,
    private val locationProviderClient: FusedLocationProviderClient,
    private val sharedViewModel: SharedViewModel
) : ViewModel() {

    @SuppressLint("MissingPermission")
    fun setMyLocationEnabled(enabled: Boolean) {
        map.isMyLocationEnabled = enabled
    }

    @SuppressLint("MissingPermission")
    fun moveToMyLocation() {
        val lastLocation = locationProviderClient.lastLocation
        lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val lastKnownLocation = task.result
                if (lastKnownLocation != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        LatLng(lastKnownLocation.latitude,
                            lastKnownLocation.longitude), DEFAULT_ZOOM))
                }
            } else {
                map.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM))
                setMyLocationEnabled(false)
            }
        }
    }

    fun addMarker(latLng: LatLng) {
        val m = map.addMarker(setMarkerOptions(latLng, getAddress(latLng), " "))
        m?.let {
            sharedViewModel.data.value?.add(it)
        }
    }

    private fun setMarkerOptions(
        location: LatLng,
        name: String,
        description: String
    ): MarkerOptions {
        return MarkerOptions().position(location).title(name).snippet(description)
    }

    private fun getAddress(location: LatLng): String {
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        return addresses[0].getAddressLine(0)
    }

    companion object {
        private const val DEFAULT_ZOOM = 15.0F

        /* Sydney's coordinates */
        private val defaultLocation = LatLng(-34.0, 151.0)
    }
}
