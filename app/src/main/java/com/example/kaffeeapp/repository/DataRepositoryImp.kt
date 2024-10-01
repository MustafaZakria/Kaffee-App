package com.example.kaffeeapp.repository

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.kaffeeapp.data.entities.DeliveryMethod
import com.example.kaffeeapp.data.entities.DeliveryMethod.AddressDelivery
import com.example.kaffeeapp.data.entities.DeliveryMethod.BranchDelivery
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.data.entities.Order
import com.example.kaffeeapp.data.local.DrinkDao
import com.example.kaffeeapp.data.local.sharedPreference.DrinkSharedPreference
import com.example.kaffeeapp.data.local.sharedPreference.ProfileSharedPreference
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.repository.interfaces.FavDrinksResult
import com.example.kaffeeapp.util.Constants.ADDRESS
import com.example.kaffeeapp.util.Constants.BRANCH_ADDRESS
import com.example.kaffeeapp.util.Constants.LATITUDE
import com.example.kaffeeapp.util.Constants.LONGITUDE
import com.example.kaffeeapp.util.Utils.generateUniqueId
import com.example.kaffeeapp.util.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepositoryImp @Inject constructor(
    private val drinkDao: DrinkDao,
    private val drinkRemoteDb: DrinkRemoteDb,
    private val drinkSharedPreference: DrinkSharedPreference,
    private val profileSharedPreference: ProfileSharedPreference
) : DataRepository {
    override suspend fun getDrinkById(id: String): Drink = drinkDao.getDrinkById(id)

    override fun isDrinkFav(id: String) = drinkSharedPreference.isDrinkFav(id)

    override suspend fun addDrinkToFav(id: String): Resource<Boolean> {
        val response = drinkRemoteDb.addFavDrink(id)
        if (response is Resource.Success) {
            drinkSharedPreference.addDrinkToFav(id)
        }
        return response
    }

    override suspend fun removeDrinkFromFav(id: String): Resource<Boolean> {
        val response = drinkRemoteDb.removeFavDrink(id)
        if (response is Resource.Success) {
            drinkSharedPreference.removeDrink(id)
        }
        return response
    }

    override suspend fun getFavDrinks(): Flow<FavDrinksResult> = flow {
        emit(Resource.Loading())
        val ids = drinkSharedPreference.getFavDrinksIds()
        val list = ids.mapNotNull { id -> getDrinkById(id) }
        emit(Resource.Success(list))
    }

    override suspend fun addOrder(
        drinkOrders: SnapshotStateList<DrinkOrder>,
        phoneNumber: String,
        totalPrice: String,
        isHomeDelivery: Boolean,
        deliveryDetail: DeliveryMethod?
    ): Resource<Boolean> {
        val orderId = generateUniqueId()
        val order = Order(
            orderId = orderId,
            timestamp = System.currentTimeMillis(),
            telephoneNumber = phoneNumber,
            isHomeDeliveryOrder = isHomeDelivery,
            totalPrice = totalPrice,
            deliveryDetails = deliveryDetail?.toMap() ?: mapOf(),
            drinkOrders = drinkOrders,
        )

        val response = addOrderToServer(order)
        if (response is Resource.Success) {
            addOrderToDatabase(orderId)
            return Resource.Success(true)
        }

        return Resource.Failure(response.exception)
    }

    override suspend fun addOrderToDatabase(orderId: String) = profileSharedPreference.addOrder(orderId)

    override suspend fun addOrderToServer(order: Order) = drinkRemoteDb.addOrderToServer(order)



}