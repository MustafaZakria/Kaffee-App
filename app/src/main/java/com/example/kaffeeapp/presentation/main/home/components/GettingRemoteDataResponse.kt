package com.example.kaffeeapp.presentation.main.home.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.util.Constants.FAILED_REMOVING_DRINK
import com.example.kaffeeapp.util.Constants.FAILED_TO_LOAD_DATA
import com.example.kaffeeapp.util.Constants.SIGNED_OUT_FAILED
import com.example.kaffeeapp.util.Constants.SIGNED_OUT_SUCCESSFULLY
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.util.snackbarStuff.SnackbarController
import com.example.kaffeeapp.util.snackbarStuff.SnackbsrEvent

@Composable
fun GettingRemoteDataResponse(
    signOutResponse: Resource<Boolean>?,
    userDataLoadingState: Resource<Boolean>?,
    onSignOutSuccess: () -> Unit
) {
    LaunchedEffect(key1 = signOutResponse) {
        if (signOutResponse is Resource.Success) {
            SnackbarController.sendEvent(
                SnackbsrEvent(
                    message = SIGNED_OUT_SUCCESSFULLY
                )
            )
            onSignOutSuccess.invoke()
        } else if(signOutResponse is Resource.Failure) {
            SnackbarController.sendEvent(
                SnackbsrEvent(
                    message = SIGNED_OUT_FAILED
                )
            )
        }
    }
    when (signOutResponse) {
        is Resource.Loading -> {
            ProgressBar(Modifier.fillMaxSize())
        }
        else -> {}
    }
    LaunchedEffect(key1 = userDataLoadingState) {
        if(userDataLoadingState is Resource.Failure) {
            SnackbarController.sendEvent(
                SnackbsrEvent(
                    message = FAILED_TO_LOAD_DATA
                )
            )
        }
    }
}