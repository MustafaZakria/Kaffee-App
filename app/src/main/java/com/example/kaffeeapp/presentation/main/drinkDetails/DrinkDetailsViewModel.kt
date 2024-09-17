package com.example.kaffeeapp.presentation.main.drinkDetails

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.repository.interfaces.MainRepository
import com.example.kaffeeapp.util.DispatcherProvider
import com.example.kaffeeapp.util.model.DrinkSize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class DrinkDetailsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private var _drink = mutableStateOf(Drink())
    val drink: State<Drink> = _drink

    fun getDrinkById(id: String) = viewModelScope.launch(dispatcherProvider.io) {
        _drink.value = mainRepository.getDrinkById(id)
    }

    fun getDrinkPrice(drinkSize: DrinkSize): String {
        Log.e("PRice", _drink.value.price.toString())
        return _drink.value.price[drinkSize.key].toString()
    }
}