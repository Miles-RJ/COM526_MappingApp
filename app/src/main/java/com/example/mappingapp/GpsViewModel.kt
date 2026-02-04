package com.example.mappingapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GpsViewModel : ViewModel() {
    var latitude: Double = 0.0
        set(newLat) {
            field=newLat
            latLiveData.value = newLat
        }
    var latLiveData = MutableLiveData<Double>()

    var longitude: Double = 0.0
        set(newLon) {
            field=newLon
            lonLiveData.value = newLon
        }
    var lonLiveData = MutableLiveData<Double>()


}