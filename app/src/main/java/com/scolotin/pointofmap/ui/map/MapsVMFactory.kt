package com.scolotin.pointofmap.ui.map

import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.scolotin.pointofmap.ui.common.SharedViewModel

class MapsVMFactory(
    private val map: GoogleMap,
    private val geocoder: Geocoder,
    private val locationProviderClient: FusedLocationProviderClient,
    private val sharedViewModel: SharedViewModel
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GoogleMap::class.java,
            Geocoder::class.java,
            FusedLocationProviderClient::class.java,
            SharedViewModel::class.java
        ).newInstance(map, geocoder, locationProviderClient, sharedViewModel)
    }
}
