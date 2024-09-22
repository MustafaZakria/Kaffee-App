package com.example.kaffeeapp.presentation.main.drinkDetails

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.data.entities.DrinkSize
import com.example.kaffeeapp.repository.DataRepositoryImp
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkDetailsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private var _drink = mutableStateOf(Drink())
    val drink: State<Drink> = _drink

    val drinkOrders = mutableStateListOf<DrinkOrder>()

    fun getDrinkById(id: String) = viewModelScope.launch(dispatcherProvider.io) {
        _drink.value = dataRepository.getDrinkById(id)
    }

    fun getDrinkPrice(drinkSize: DrinkSize): String {
        return _drink.value.price[drinkSize.key].toString()
    }

    fun isDrinkFav() = dataRepository.isDrinkFav(_drink.value.id)

    fun addDrinkToFav() = viewModelScope.launch(dispatcherProvider.io) {
        dataRepository.addDrinkToFav(_drink.value.id)
    }

    fun removeDrinkFromFav() = viewModelScope.launch(dispatcherProvider.io) {
        dataRepository.removeDrinkFromFav(_drink.value.id)
    }

    fun addDrinkToCart(size: DrinkSize) {
        _drink.value.apply {
            val price = price[size.key] ?: "0"
            drinkOrders.add(
                DrinkOrder(
                    name = name,
                    id = id,
                    size = size.shortened,
                    price = price
                )
            )
        }
        Log.d("TEST: ", drinkOrders.toString())
    }

    fun removeDrinkFromCart(drinkOrder: DrinkOrder) {
        drinkOrders.remove(drinkOrder)
    }
}