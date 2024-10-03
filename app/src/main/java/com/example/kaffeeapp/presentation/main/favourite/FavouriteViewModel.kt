package com.example.kaffeeapp.presentation.main.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.repository.interfaces.FavDrinksResult
import com.example.kaffeeapp.util.Constants.DRINK_REMOVED_SUCCESSFULLY
import com.example.kaffeeapp.util.Constants.FAILED_REMOVING_DRINK
import com.example.kaffeeapp.util.DispatcherProvider
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.util.snackbarStuff.SnackbarController
import com.example.kaffeeapp.util.snackbarStuff.SnackbsrEvent
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

    init {
        getFavDrinks()
    }

    private fun getFavDrinks() = viewModelScope.launch(dispatcherProvider.io) {
        favDrinks = dataRepositoryImp.getFavDrinks()
    }

    fun removeFavDrink(id: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            val response = dataRepositoryImp.removeDrinkFromFav(id)
            if (response is Resource.Success) {
                SnackbarController.sendEvent(
                    SnackbsrEvent(
                        message = DRINK_REMOVED_SUCCESSFULLY
                    )
                )
            } else if (response is Resource.Failure) {
                SnackbarController.sendEvent(
                    SnackbsrEvent(
                        message = FAILED_REMOVING_DRINK
                    )
                )
            }
        }
        getFavDrinks()
    }
}