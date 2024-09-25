package com.example.kaffeeapp.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.kaffeeapp.data.entities.DeliveryMethod
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.data.local.DrinkDao
import com.example.kaffeeapp.data.local.sharedPreference.DrinkSharedPreference
import com.example.kaffeeapp.data.local.sharedPreference.OrderSharedPreference
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.repository.interfaces.FavDrinksResult
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
    private val orderSharedPreference: OrderSharedPreference
) : DataRepository {
    override suspend fun getDrinkById(id: String): Drink = drinkDao.getDrinkById(id)

    override fun isDrinkFav(id: String) = drinkSharedPreference.isDrinkFav(id)

    override suspend fun addDrinkToFav(id: String) {
        drinkSharedPreference.addDrinkToFav(id)
        drinkRemoteDb.addFavDrink(id)
    }

    override suspend fun removeDrinkFromFav(id: String) {
        drinkSharedPreference.removeDrink(id)
        drinkRemoteDb.removeFavDrink(id)
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
        deliveryDetail: DeliveryMethod
    ) {

    }

    override suspend fun addOrderToUser() {

    }

    override suspend fun addOrderToServer() {

    }

}