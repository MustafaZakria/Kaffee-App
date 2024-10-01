package com.example.kaffeeapp.presentation.main.favourite

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.repository.interfaces.FavDrinksResult
import com.example.kaffeeapp.util.DispatcherProvider
import com.example.kaffeeapp.util.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val dataRepositoryImp: DataRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var favDrinks = flowOf<FavDrinksResult>()
    var removeDrinkResponse by mutableStateOf<Resource<Boolean>?>(null)

    init {
        getFavDrinks()
    }

    private fun getFavDrinks() = viewModelScope.launch(dispatcherProvider.io) {
        favDrinks = dataRepositoryImp.getFavDrinks()
    }

    fun removeFavDrink(id: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            removeDrinkResponse = dataRepositoryImp.removeDrinkFromFav(id)
        }
        getFavDrinks()
    }

    fun resetRemoveState() {
        removeDrinkResponse = null
    }
}