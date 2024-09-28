package com.example.kaffeeapp.repository.interfaces

import androidx.lifecycle.LiveData
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.util.model.SelectedType

interface MainRepository {

    suspend fun refreshDrinks(): Resource<Boolean>

    fun getAllDrinks(type: SelectedType): LiveData<List<Drink>>

    fun getAllDrinksBySearch(drink: String): LiveData<List<Drink>>

    suspend fun getDrinkById(id: String): Drink

    suspend fun refreshUserData(): Resource<Boolean>

    fun signOut(): Resource<Boolean>
}