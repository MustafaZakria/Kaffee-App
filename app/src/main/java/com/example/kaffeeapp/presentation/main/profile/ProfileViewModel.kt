package com.example.kaffeeapp.presentation.main.profile

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.repository.interfaces.OrdersResponse
import com.example.kaffeeapp.repository.interfaces.ProfileRepository
import com.example.kaffeeapp.util.Constants.FAILURE_PROFILE_PICTURE
import com.example.kaffeeapp.util.Constants.SUCCESS_PROFILE_PICTURE
import com.example.kaffeeapp.util.DispatcherProvider
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.util.snackbarStuff.SnackbarController
import com.example.kaffeeapp.util.snackbarStuff.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var orders = flowOf<OrdersResponse>()

    var user by mutableStateOf(User())

    var uploadingImageState by mutableStateOf<Resource<String>?>(null)

    val _isSystemOnDarkMode = mutableStateOf(profileRepository.getIsSystemOnDarkMode())
    val isSystemOnDarkMode: State<Boolean> = _isSystemOnDarkMode

    init {
        viewModelScope.launch(dispatcherProvider.io) {
            orders = profileRepository.getAllResources()
        }
        viewModelScope.launch(dispatcherProvider.io) {
            profileRepository.getUser().collect { value ->
                user = value
            }
        }
        viewModelScope.launch(dispatcherProvider.io) {
            profileRepository.isSystemOnDarkMode().collect { value ->
                _isSystemOnDarkMode.value = value
            }
        }
    }

    fun changeSystemMode() = profileRepository.changeSystemMode()

    fun setUserImage(uri: Uri?) {
        uploadingImageState = Resource.Loading()
        viewModelScope.launch(dispatcherProvider.io) {
            uploadingImageState = profileRepository.setUserImage(uri)
            if (uploadingImageState is Resource.Success) {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = SUCCESS_PROFILE_PICTURE
                    )
                )
            } else if (uploadingImageState is Resource.Failure) {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = FAILURE_PROFILE_PICTURE
                    )
                )
            }
        }
    }
}