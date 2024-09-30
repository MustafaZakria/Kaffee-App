package com.example.kaffeeapp.presentation.main.profile

import android.graphics.Picture
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.repository.interfaces.OrdersResponse
import com.example.kaffeeapp.repository.interfaces.ProfileRepository
import com.example.kaffeeapp.util.DispatcherProvider
import com.example.kaffeeapp.util.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var orders by mutableStateOf<OrdersResponse>(Resource.Loading())
    val user = profileRepository.getUser()
    var points by mutableIntStateOf(0)



    fun getOrders() {
        viewModelScope.launch(dispatcherProvider.io) {
            orders = profileRepository.getAllResources()
        }
    }
}