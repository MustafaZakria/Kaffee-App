package com.example.kaffeeapp.repository.interfaces

import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.util.model.Resource
import kotlinx.coroutines.flow.Flow

typealias FavDrinksResult = Resource<List<Drink>>

interface DataRepository {

    suspend fun getDrinkById(id: String): Drink

    fun isDrinkFav(id: String): Boolean

    suspend fun addDrinkToFav(id: String)

    suspend fun removeDrinkFromFav(id: String)

    suspend fun getFavDrinks(): Flow<FavDrinksResult>
}