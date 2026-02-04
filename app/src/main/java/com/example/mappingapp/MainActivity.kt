package com.example.mappingapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Paint.Align
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mappingapp.ui.theme.MappingAppTheme

class MainActivity : ComponentActivity(), LocationListener {
    val viewModel : GpsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startGPS()
        setContent {
            displayLatLon()
            MappingAppTheme {
            }
        }
    }

    @Composable
    fun displayLatLon() {

        // Get Lat and on values from view model
        val lat = remember { mutableStateOf(0.0) }
        viewModel.latLiveData.observe(this){
            lat.value = it
        }
        val lon = remember { mutableStateOf(0.0) }
        viewModel.latLiveData.observe(this){
            lon.value = it
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(20.dp),
                horizontalArrangement = Arrangement.Start
            ) {

                val latStr = "Latitude: ${lon.value}"
                Text(latStr)
            }
            Row(
                modifier = Modifier.fillMaxWidth().height(20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                val lonStr = "Longitude: ${lon.value}"
                Text(lonStr)
            }
        }
    }

    /**
     * Checks the app has access to fine location
     */
    private fun checkLocationPermissions(successCallback: () -> Unit) {
        val requiredPermission = Manifest.permission.ACCESS_FINE_LOCATION

        if (checkSelfPermission(requiredPermission) == PackageManager.PERMISSION_GRANTED) {
            successCallback()
        }
        else {
            val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    successCallback()
                }
                else {
                    Toast.makeText(this, "GPS permission not granted", Toast.LENGTH_LONG).show()
                }
            }
            permissionLauncher.launch(requiredPermission)
        }

    }

    /**
     * starts the GPS if permissions are enabled
     */
    @SuppressLint("MissingPermission")
    fun startGPS() {
        checkLocationPermissions {
            Toast.makeText(this, "GPS permission granted", Toast.LENGTH_LONG).show()
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        }
    }

    /**
     * updates state variables every time location changes
     */
    override fun onLocationChanged(location: Location) {
        viewModel.let {
            it.latitude = location.latitude
            it.longitude = location.longitude
        }
    }

    @Deprecated("don't use this")
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

}
