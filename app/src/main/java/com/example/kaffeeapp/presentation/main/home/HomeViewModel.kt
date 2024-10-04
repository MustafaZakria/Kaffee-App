package com.example.kaffeeapp.presentation.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.repository.interfaces.MainRepository
import com.example.kaffeeapp.util.DispatcherProvider
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.util.model.SelectedType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _drinkSelectedType = MutableLiveData(SelectedType.ALL_DRINKS)
    val drinkSelectedType: LiveData<SelectedType> = _drinkSelectedType

    var drinksResponse by mutableStateOf<Resource<Boolean>>(Resource.Success(false))

    private var _searchValueState = MutableLiveData("")
    val searchValueState: LiveData<String> = _searchValueState

    var drinks: LiveData<List<Drink>> = MutableLiveData()

    private val drinksFromSelectType: LiveData<List<Drink>> = _drinkSelectedType.switchMap { type ->
        mainRepository.getAllDrinks(type)
    }
    var userDataResponse by mutableStateOf<Resource<Boolean>?>(null)

    init {
        drinksResponse = Resource.Loading()

        viewModelScope.launch(dispatcherProvider.io) {
            drinksResponse = mainRepository.refreshDrinks()
            userDataResponse = mainRepository.refreshUserData()
            mainRepository.refreshMainData()
        }

        drinks = drinksFromSelectType
    }

    fun setSelectedType(type: SelectedType) {
        _drinkSelectedType.value = type
    }

    fun onSearchValueChange(value: String) {
        value.let {
            _searchValueState.value = it
            drinks = if (it != "") {
                mainRepository.getAllDrinksBySearch(it)
            } else {
                drinksFromSelectType
            }
        }
    }

    var signOutResponse by mutableStateOf<Resource<Boolean>?>(null)

    fun signOut() {
        signOutResponse = Resource.Loading()
        viewModelScope.launch(dispatcherProvider.io) {
            signOutResponse = mainRepository.signOut()
        }
    }


}