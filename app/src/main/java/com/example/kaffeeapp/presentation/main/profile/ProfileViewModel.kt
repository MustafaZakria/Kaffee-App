package com.example.kaffeeapp.presentation.main.profile

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.repository.interfaces.OrdersResponse
import com.example.kaffeeapp.repository.interfaces.ProfileRepository
import com.example.kaffeeapp.util.DispatcherProvider
import com.example.kaffeeapp.util.model.Resource
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
    var user = profileRepository.getUser()
    var points by mutableIntStateOf(0)

    var uploadingImageState by mutableStateOf<Resource<String>?>(null)

    init {
        viewModelScope.launch(dispatcherProvider.io) {
            orders = profileRepository.getAllResources()
        }
    }

    fun setUserImage(uri: Uri?) {
        uploadingImageState = Resource.Loading()
        viewModelScope.launch(dispatcherProvider.io) {
            uploadingImageState = profileRepository.setUserImage(uri)
        }
    }

    fun resetImageState() {
        uploadingImageState = null
    }
}