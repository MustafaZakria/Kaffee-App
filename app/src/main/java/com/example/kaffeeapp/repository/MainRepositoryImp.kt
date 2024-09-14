package com.example.kaffeeapp.repository

import androidx.lifecycle.LiveData
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.local.DrinkDao
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.repository.interfaces.MainRepository
import com.example.kaffeeapp.util.model.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImp @Inject constructor(
    private val drinkDao: DrinkDao,
    private val drinkRemoteDb: DrinkRemoteDb
) : MainRepository {

    override suspend fun refreshDrinks(): Resource<Boolean> {
        return try {
            when (val drinkResponse = drinkRemoteDb.getAllDrinks()) {
                is Resource.Success -> {
                    drinkResponse.data?.let {
                        drinkDao.insert(it)
                    }
                    Resource.Success(true)
                }

                is Resource.Failure -> {
                    throw Exception(drinkResponse.exception)
                }

                is Resource.Loading -> {
                    Resource.Loading()
                }
            }
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override fun getAllDrinks(type: SelectedType): LiveData<List<Drink>> = when (type) {
        SelectedType.HOT_DRINKS -> drinkDao.getAllHotDrinks()
        SelectedType.COLD_DRINKS -> drinkDao.getAllColdDrinks()
        SelectedType.ALL_DRINKS -> drinkDao.getAllDrinks()
        else -> drinkDao.getDrinksByIngredients("%${type.value}%")
    }

    override fun getAllDrinksBySearch(drink: String): LiveData<List<Drink>> =
        drinkDao.getDrinksBySearch("%$drink%")

}

enum class SelectedType(val value: String) {
    ALL_DRINKS("All"),
    HOT_DRINKS("Hot"),
    COLD_DRINKS("Cold"),
    TEA("Tea"),
    MATCHA("Matcha"),
    COFFEE("Coffee")
}