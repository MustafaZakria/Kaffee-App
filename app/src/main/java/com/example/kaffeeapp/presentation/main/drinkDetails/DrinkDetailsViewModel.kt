package com.example.kaffeeapp.presentation.main.drinkDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkSize
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.util.Constants.ID_KEY
import com.example.kaffeeapp.util.DispatcherProvider
import com.example.kaffeeapp.util.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dataRepository: DataRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private var _drink = mutableStateOf(Drink())
    val drink: State<Drink> = _drink

    var removeFavDrinkResponse by mutableStateOf<Resource<Boolean>?>(null)
    var addFavDrinkResponse by mutableStateOf<Resource<Boolean>?>(null)

    init {
        val id = savedStateHandle.get<String>(ID_KEY)
        if (id != null) {
            viewModelScope.launch(dispatcherProvider.io) {
                _drink.value = dataRepository.getDrinkById(id)
            }
        }
    }

    fun getDrinkPrice(drinkSize: DrinkSize): String {
        return _drink.value.price[drinkSize.key].toString()
    }

    fun isDrinkFav() = dataRepository.isDrinkFav(_drink.value.id)

    fun addDrinkToFav() = viewModelScope.launch(dispatcherProvider.io) {
        addFavDrinkResponse = dataRepository.addDrinkToFav(_drink.value.id)
    }

    fun removeDrinkFromFav() = viewModelScope.launch(dispatcherProvider.io) {
        removeFavDrinkResponse = dataRepository.removeDrinkFromFav(_drink.value.id)
    }
}