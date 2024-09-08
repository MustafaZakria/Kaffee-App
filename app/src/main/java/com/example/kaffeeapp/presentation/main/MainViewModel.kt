package com.example.kaffeeapp.presentation.main

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
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val drinkSelectedType = MutableLiveData(SelectedType.ALL_DRINKS)

    val drinks: LiveData<List<Drink>> = drinkSelectedType.switchMap { type ->
        mainRepository.getAllDrinks(type)
    }

    init {
        viewModelScope.launch {
            mainRepository.refreshDrinks()
        }
    }
}