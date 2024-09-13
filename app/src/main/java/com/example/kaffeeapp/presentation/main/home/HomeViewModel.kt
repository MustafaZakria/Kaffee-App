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
import com.example.kaffeeapp.repository.SelectedType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val drinkSelectedType = MutableLiveData(SelectedType.ALL_DRINKS)

    private var searchValueState by mutableStateOf("")
    private val isSearching by mutableStateOf(searchValueState != "")

    val drinks: LiveData<List<Drink>> = drinkSelectedType.switchMap { type ->
        if(!isSearching) {
            mainRepository.getAllDrinks(type)
        } else {
            mainRepository.getAllDrinks(type)
        }
    }

    fun setSelectedType(type: SelectedType) {
        drinkSelectedType.value = type
    }

    fun onSearchValueChange(value: String) {
        searchValueState = value
    }

    init {
        viewModelScope.launch {
            mainRepository.refreshDrinks()
        }
    }
}