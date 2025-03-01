package com.example.kaffeeapp.repository.interfaces

import com.example.kaffeeapp.data.entities.BranchDetails
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.Order
import com.example.kaffeeapp.presentation.main.cart.models.OrderUi
import com.example.kaffeeapp.util.model.Resource
import kotlinx.coroutines.flow.Flow

typealias FavDrinksResult = Resource<List<Drink>>
typealias BranchesResult = Resource<List<BranchDetails>>

interface DataRepository {

    suspend fun getDrinkById(id: String): Drink

    fun isDrinkFav(id: String): Boolean

    suspend fun addDrinkToFav(id: String): Resource<Boolean>

    suspend fun removeDrinkFromFav(id: String): Resource<Boolean>

    suspend fun getFavDrinks(): Flow<FavDrinksResult>

    suspend fun addOrder(
        order: Order
    ): Resource<Boolean>

    suspend fun addOrderToDatabase(orderId: String)

    suspend fun addOrderToServer(order: Order): Resource<Boolean>

    fun getPromoCodeValue(promoCode: String): String?

    suspend fun getBranchesDetails(): Flow<BranchesResult>

    suspend fun updateUserPoints()
}