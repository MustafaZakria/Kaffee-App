package com.example.kaffeeapp.repository.interfaces

import androidx.lifecycle.LiveData
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.repository.SelectedType

interface MainRepository {

    suspend fun refreshDrinks()

    fun getAllDrinks(type: SelectedType): LiveData<List<Drink>>

}