package com.example.mappingapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.maplibre.android.geometry.LatLng

class GpsViewModel : ViewModel() {
    var latLon = LatLng(0.0, 0.0)
        set(newLatLon) {
            field = newLatLon
            latLonLiveData.value = newLatLon
        }
    var latLonLiveData = MutableLiveData<LatLng>()

}