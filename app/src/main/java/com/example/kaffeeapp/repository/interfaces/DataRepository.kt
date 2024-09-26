package com.example.kaffeeapp.repository.interfaces

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.kaffeeapp.data.entities.DeliveryMethod
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.data.entities.Order
import com.example.kaffeeapp.util.model.Resource
import kotlinx.coroutines.flow.Flow

typealias FavDrinksResult = Resource<List<Drink>>

interface DataRepository {

    suspend fun getDrinkById(id: String): Drink

    fun isDrinkFav(id: String): Boolean

    suspend fun addDrinkToFav(id: String)

    suspend fun removeDrinkFromFav(id: String)

    suspend fun getFavDrinks(): Flow<FavDrinksResult>
    suspend fun addOrder(
        drinkOrders: SnapshotStateList<DrinkOrder>,
        phoneNumber: String,
        totalPrice: String,
        isHomeDelivery: Boolean,
        deliveryDetail: DeliveryMethod?
    ): Flow<Resource<Boolean>>

    suspend fun addOrderToDatabase(orderId: String)

    suspend fun addOrderToServer(order: Order): Resource<Boolean>
}