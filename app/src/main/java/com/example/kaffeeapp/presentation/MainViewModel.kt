package com.example.kaffeeapp.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.data.local.sharedPreference.MainSharedPreference
import com.example.kaffeeapp.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainSharedPreference: MainSharedPreference,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _isSystemOnDarkMode = mutableStateOf(mainSharedPreference.getIsSystemOnDarkMode())
    val isSystemOnDarkMode: State<Boolean> get() = _isSystemOnDarkMode

    init {
        viewModelScope.launch(dispatcherProvider.io) {
            mainSharedPreference.isSystemOnDarkModeFlow().collect { value ->
                _isSystemOnDarkMode.value = value
            }
        }
    }
}