package com.example.kaffeeapp.presentation.main.drinkDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.repository.interfaces.MainRepository
import com.example.kaffeeapp.util.DispatcherProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class DrinkDetailsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private var _drink by mutableStateOf(Drink())
    val drink: Drink = _drink

    fun getDrinkById(id: String) = viewModelScope.launch(dispatcherProvider.io) {
        _drink = mainRepository.getDrinkById(id)
    }
}