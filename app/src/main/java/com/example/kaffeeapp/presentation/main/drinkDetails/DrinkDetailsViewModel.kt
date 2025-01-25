package com.example.kaffeeapp.presentation.main.drinkDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.util.model.DrinkSize
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.util.Constants.DRINK_ADDED_SUCCESSFULLY
import com.example.kaffeeapp.util.Constants.DRINK_REMOVED_SUCCESSFULLY
import com.example.kaffeeapp.util.Constants.FAILED_ADDING_DRINK
import com.example.kaffeeapp.util.Constants.FAILED_REMOVING_DRINK
import com.example.kaffeeapp.util.Constants.ID_KEY
import com.example.kaffeeapp.util.DispatcherProvider
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.util.snackbarStuff.SnackbarController
import com.example.kaffeeapp.util.snackbarStuff.SnackbarEvent
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
        val response = dataRepository.addDrinkToFav(_drink.value.id)
        if (response is Resource.Success) {
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = DRINK_ADDED_SUCCESSFULLY
                )
            )
        } else if (response is Resource.Failure) {
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = FAILED_ADDING_DRINK
                )
            )
        }
    }

    fun removeDrinkFromFav() = viewModelScope.launch(dispatcherProvider.io) {
        val response = dataRepository.removeDrinkFromFav(_drink.value.id)
        if (response is Resource.Success) {
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = DRINK_REMOVED_SUCCESSFULLY
                )
            )
        } else if (response is Resource.Failure) {
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = FAILED_REMOVING_DRINK
                )
            )
        }
    }
}