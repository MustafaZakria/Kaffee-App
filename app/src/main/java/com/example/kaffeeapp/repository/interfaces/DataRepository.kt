package com.example.kaffeeapp.repository.interfaces

import com.example.kaffeeapp.data.entities.Drink

interface DataRepository {

    suspend fun getDrinkById(id: String): Drink

    fun isDrinkFav(id: String): Boolean

    suspend fun addDrinkToFav(id: String)

    suspend fun removeDrinkFromFav(id: String)

    fun getFavDrinks(): List<String>
}