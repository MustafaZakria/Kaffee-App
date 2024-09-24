package com.example.kaffeeapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.local.DrinkDao
import com.example.kaffeeapp.data.local.sharedPreference.DrinkSharedPreference
import com.example.kaffeeapp.data.local.sharedPreference.OrderSharedPreference
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.repository.interfaces.MainRepository
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.util.model.SelectedType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImp @Inject constructor(
    private val drinkDao: DrinkDao,
    private val drinkRemoteDb: DrinkRemoteDb,
    private val drinkSharedPreference: DrinkSharedPreference,
    private val orderSharedPreference: OrderSharedPreference
) : MainRepository {
    override suspend fun refreshDrinks(): Resource<Boolean> {
        return when (val drinkResponse = drinkRemoteDb.getAllDrinks()) {
            is Resource.Success -> {
                drinkResponse.data?.let {
                    drinkDao.insert(it)
                }
                Resource.Success(true)
            }

            else -> {
                Resource.Failure(drinkResponse.exception)
            }
        }
    }

    override suspend fun refreshData() {
        refreshFavouriteDrinks()
        refreshOrders()
    }

    override fun getAllDrinks(type: SelectedType): LiveData<List<Drink>> = when (type) {
        SelectedType.HOT_DRINKS -> drinkDao.getAllHotDrinks()
        SelectedType.COLD_DRINKS -> drinkDao.getAllColdDrinks()
        SelectedType.ALL_DRINKS -> drinkDao.getAllDrinks()
        else -> drinkDao.getDrinksByIngredients("%${type.value}%")
    }

    override fun getAllDrinksBySearch(drink: String): LiveData<List<Drink>> =
        drinkDao.getDrinksBySearch("%$drink%")

    override suspend fun getDrinkById(id: String): Drink = drinkDao.getDrinkById(id)

    override suspend fun refreshFavouriteDrinks() {
        val ids = drinkRemoteDb.getFavDrinksIds()
        drinkSharedPreference.insertFavDrinksList(ids)
    }

    override suspend fun refreshOrders() {
        val ids = drinkRemoteDb.getOrdersIds()
        orderSharedPreference.insertOrdersList(ids)
    }

}