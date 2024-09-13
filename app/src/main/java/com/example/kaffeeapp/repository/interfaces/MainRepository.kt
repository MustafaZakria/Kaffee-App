package com.example.kaffeeapp.repository.interfaces

import androidx.lifecycle.LiveData
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.repository.SelectedType
import com.example.kaffeeapp.util.model.Resource

interface MainRepository {

    suspend fun refreshDrinks(): Resource<Boolean>

    fun getAllDrinks(type: SelectedType): LiveData<List<Drink>>

}