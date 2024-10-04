package com.example.kaffeeapp.repository

import android.util.Log
import com.example.kaffeeapp.data.entities.BranchDetails
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.Order
import com.example.kaffeeapp.data.local.DrinkDao
import com.example.kaffeeapp.data.local.sharedPreference.MainSharedPreference
import com.example.kaffeeapp.data.local.sharedPreference.UserSharedPreference
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.presentation.main.cart.models.CartDetails
import com.example.kaffeeapp.repository.interfaces.BranchesResult
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.repository.interfaces.FavDrinksResult
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
    private val userSharedPreference: UserSharedPreference,
    private val mainSharedPreference: MainSharedPreference
) : DataRepository {
    override suspend fun getDrinkById(id: String): Drink = drinkDao.getDrinkById(id)

    override fun isDrinkFav(id: String) =
        userSharedPreference.getFavDrinksIds().contains(id)

    override suspend fun addDrinkToFav(id: String): Resource<Boolean> {
        val response = drinkRemoteDb.addFavDrink(id)
        if (response is Resource.Success) {
            userSharedPreference.addDrinkToFav(id)
        }
        return response
    }

    override suspend fun removeDrinkFromFav(id: String): Resource<Boolean> {
        val response = drinkRemoteDb.removeFavDrink(id)
        if (response is Resource.Success) {
            userSharedPreference.removeDrink(id)
        }
        return response
    }

    override suspend fun getFavDrinks(): Flow<FavDrinksResult> = flow {
        emit(Resource.Loading())
        Log.d("flow", "emitting")
        val ids = userSharedPreference.getFavDrinksIds()
        val list = ids.mapNotNull { id -> getDrinkById(id) }
        emit(Resource.Success(list))
    }

    override suspend fun addOrder(
        cartDetails: CartDetails,
        totalPrice: String,
    ): Resource<Boolean> {
        val orderId = generateUniqueId()
        val order = Order(
            orderId = orderId,
            timestamp = System.currentTimeMillis(),
            telephoneNumber = cartDetails.phoneNumberValue,
            isHomeDeliveryOrder = cartDetails.isDeliveryEnabled,
            totalPrice = totalPrice,
            deliveryDetails = cartDetails.deliveryValue?.toMap() ?: mapOf(),
            drinkOrders = cartDetails.drinkOrders,
            note = cartDetails.note
        )

        val response = addOrderToServer(order)
        if (response is Resource.Success) {
            addOrderToDatabase(orderId)
            return Resource.Success(true)
        }
        return Resource.Failure(response.exception)
    }

    override suspend fun addOrderToDatabase(orderId: String) =
        userSharedPreference.addOrder(orderId)

    override suspend fun addOrderToServer(order: Order) =
        drinkRemoteDb.addOrderToServer(order)

    override fun getPromoCodeValue(promoCode: String): String? {
        val map = mainSharedPreference.getPromoCodes()
        if(map.containsKey(promoCode)) {
            return map[promoCode]
        }
        return null
    }

    override suspend fun getBranchesDetails(): Flow<BranchesResult> = flow {
        emit(Resource.Loading())
        emit(drinkRemoteDb.getBranches())
    }
}