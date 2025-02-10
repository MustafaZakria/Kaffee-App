package com.example.kaffeeapp.repository.implementations

import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.Order
import com.example.kaffeeapp.data.local.DrinkDao
import com.example.kaffeeapp.data.local.sharedPreference.MainSharedPreference
import com.example.kaffeeapp.data.local.sharedPreference.UserSharedPreference
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.repository.interfaces.BranchesResult
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.repository.interfaces.FavDrinksResult
import com.example.kaffeeapp.util.Constants.POINTS_ADDED_PER_ORDER
import com.example.kaffeeapp.util.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

    override suspend fun getFavDrinks(): Flow<FavDrinksResult> {
        return userSharedPreference.getUserFavFlow()
            .distinctUntilChanged()
            .map { value ->
                try {
                    if (value.isNullOrEmpty()) {
                        return@map Resource.Success(emptyList())
                    }
                    val list = value.split(",").mapNotNull { id ->
                        getDrinkById(id)
                    }
                    Resource.Success(list)
                } catch (e: Exception) {
                    Resource.Failure(e)
                }
            }
    }

    override suspend fun addOrder(
        order: Order
    ): Resource<Boolean> {
        val response = addOrderToServer(order)
        if (response is Resource.Success) {
            addOrderToDatabase(order.orderId)
        }
        return response
    }

    override suspend fun addOrderToDatabase(orderId: String) =
        userSharedPreference.addOrder(orderId)

    override suspend fun addOrderToServer(order: Order) =
        drinkRemoteDb.addOrderToServer(order)

    override fun getPromoCodeValue(promoCode: String): String? {
        val map = mainSharedPreference.getPromoCodes()
        if (map.containsKey(promoCode)) {
            return map[promoCode]
        }
        return null
    }

    override suspend fun getBranchesDetails(): Flow<BranchesResult> = flow {
        emit(drinkRemoteDb.getBranches())
    }

    override suspend fun updateUserPoints() {
        val points = userSharedPreference.getUserPoints()?.toInt() ?: 0
        val updatedPoints = points + POINTS_ADDED_PER_ORDER
        val response = drinkRemoteDb.setUserPoints(updatedPoints.toString())
        if (response is Resource.Success) {
            userSharedPreference.setUserPoints(updatedPoints.toString())
        }
    }
}


