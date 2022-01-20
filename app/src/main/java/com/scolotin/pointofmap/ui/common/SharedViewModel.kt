package com.scolotin.pointofmap.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.Marker

open class SharedViewModel : ViewModel() {
    private val sharedLiveData = MutableLiveData<ArrayList<Marker>>(arrayListOf())
    val data: MutableLiveData<ArrayList<Marker>> = sharedLiveData

    fun updateData() {
        sharedLiveData.value = sharedLiveData.value
    }
}
