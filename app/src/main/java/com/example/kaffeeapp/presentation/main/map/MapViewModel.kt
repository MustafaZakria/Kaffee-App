package com.example.kaffeeapp.presentation.main.map

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.lifecycle.ViewModel
import com.example.kaffeeapp.util.DispatcherProvider
import com.example.kaffeeapp.util.model.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationManager: LocationManager,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(): Flow<Resource<Location>> {
        return flow {
            emit(Resource.Loading())

            val location = fusedLocationProviderClient.lastLocation.await()
            if (location != null) {
                emit(Resource.Success(location))
            } else {
                emit(Resource.Failure(Exception("Last known location is null")))
            }

            return@flow // Close the flow after sending the location or an error
        }
    }

    suspend fun getLocationTitle(geocoder: Geocoder, location: LatLng): String? {
        return withContext(dispatcherProvider.io) {
            var address: Address? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(location.latitude, location.longitude, 1) {
                    address = it.firstOrNull()
                }
            } else {
                address = geocoder.getFromLocation(location.latitude, location.longitude, 1)?.firstOrNull()
            }
            if (address != null) {
                address?.locality ?: address?.countryName // Return the locality or country name
            } else {
                null
            }
        }
    }
}
