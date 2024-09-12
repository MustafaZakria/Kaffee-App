package com.example.kaffeeapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.local.DrinkDao
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.repository.interfaces.MainRepository
import com.example.kaffeeapp.util.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepositoryImp @Inject constructor(
    private val drinkDao: DrinkDao,
    private val drinkRemoteDb: DrinkRemoteDb
) : MainRepository {

    override suspend fun refreshDrinks() {
        try {
            when (val drinkResponse = drinkRemoteDb.getAllDrinks()) {
                is Resource.Success -> {
                    drinkResponse.data?.let {
                        withContext(Dispatchers.IO) {
                            drinkDao.insert(it)
                        }
                    }
                }

                is Resource.Failure -> {
                    throw Exception(drinkResponse.exception)
                }
                else -> Unit
            }
        } catch (e: Exception) {
            Log.e("Main Repository", e.message.toString())
        }
    }

    override fun getAllDrinks(type: SelectedType): LiveData<List<Drink>> = when (type) {
        SelectedType.HOT_DRINKS -> drinkDao.getAllHotDrinks()
        SelectedType.COLD_DRINKS -> drinkDao.getAllColdDrinks()
        else -> drinkDao.getAllDrinks()
    }
}

enum class SelectedType {
    ALL_DRINKS,
    HOT_DRINKS,
    COLD_DRINKS,
    TEA,
    MATCHA,
    COFFEE,
    SEARCHED_DRINK
}