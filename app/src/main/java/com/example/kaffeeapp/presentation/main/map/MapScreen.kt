package com.example.kaffeeapp.presentation.main.map

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.entities.DeliveryType
import com.example.kaffeeapp.presentation.main.cart.CartViewModel
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.util.Constants.MAP_ZOOM
import com.example.kaffeeapp.util.model.Resource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapScreen(
    orderDetailsViewModel: CartViewModel,
    mapViewModel: MapViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val geocoder = Geocoder(context)

    var location by rememberSaveable { mutableStateOf(LatLng(0.0, 0.0)) }
    var title by rememberSaveable { mutableStateOf("") }

    RequestLocationPermission(context = context) {
        scope.launch {
            mapViewModel.getCurrentLocation().collect { resource ->
                if (resource is Resource.Success && resource.data != null) {
                    location = LatLng(resource.data.latitude, resource.data.longitude)
                }
            }
        }
    }

    LaunchedEffect(key1 = location.latitude) {
        scope.launch {
            title = mapViewModel.getLocationTitle(geocoder, location) ?: ""
        }
    }

    val cameraPositionState = rememberCameraPositionState()

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.primary
                    )
                    .clickable {
                        val address = DeliveryType.HomeDelivery(
                            title,
                            location.latitude,
                            location.longitude
                        )
                        orderDetailsViewModel.setDeliveryMethod(address)
                        navigateBack.invoke()
                    },
                contentAlignment = Alignment.Center
            ) {
                CustomizedText(
                    text = stringResource(id = R.string.submit),
                    fontSize = dimensionResource(id = R.dimen.text_size_18),
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium)),
                    textAlign = TextAlign.Center
                )
            }
        }
    ) { padding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
        ) {

            GoogleMap(
                modifier = Modifier,
                cameraPositionState = cameraPositionState,
                onMapLongClick = { latLng ->
                    location = latLng
                }
            ) {
                MapEffect(key1 = location) {
                    scope.launch {
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.newLatLngZoom(
                                location,
                                MAP_ZOOM
                            )
                        )
                    }
                }
                val markerState = MarkerState(position = location)
                Marker(
                    state = markerState,
                    draggable = true
                )
            }
        }

    }
}

@Composable
fun RequestLocationPermission(
    context: Context,
    permissionGranted: () -> Unit
) {
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            permissionGranted.invoke()
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            permissionGranted.invoke()
        } else {
            //ask for permission
            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }
}

