package com.example.kaffeeapp.repository

import androidx.lifecycle.LiveData
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.data.local.DrinkDao
import com.example.kaffeeapp.data.local.sharedPreference.MainSharedPreference
import com.example.kaffeeapp.data.local.sharedPreference.UserSharedPreference
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
    private val userSharedPreference: UserSharedPreference,
    private val mainSharedPreference: MainSharedPreference
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

    override fun getAllDrinks(type: SelectedType): LiveData<List<Drink>> = when (type) {
        SelectedType.HOT_DRINKS -> drinkDao.getAllHotDrinks()
        SelectedType.COLD_DRINKS -> drinkDao.getAllColdDrinks()
        SelectedType.ALL_DRINKS -> drinkDao.getAllDrinks()
        else -> drinkDao.getDrinksByIngredients("%${type.value}%")
    }

    override fun getAllDrinksBySearch(drink: String): LiveData<List<Drink>> =
        drinkDao.getDrinksBySearch("%$drink%")

    override suspend fun refreshUserData(): Resource<Boolean> {
        val userResponse = drinkRemoteDb.getUser()
        if (userResponse is Resource.Success) {
            val user = userResponse.data ?: User()
            userSharedPreference.addUserInfo(
                name = user.name,
                email = user.email,
                imageUrl = user.imageUrl,
                orders = user.orders,
                favourites = user.favouriteDrinks,
                points = user.points
            )
            return Resource.Success(true)
        }
        return Resource.Failure(userResponse.exception)
    }

    override suspend fun refreshMainData(): Resource<Boolean> {
        val promoResponse = drinkRemoteDb.getPromoCodes()
        if (promoResponse is Resource.Success) {
            mainSharedPreference.insertPromoCodes(promoResponse.data ?: mapOf())
            return Resource.Success(true)
        }
        return Resource.Failure(promoResponse.exception)
    }

    override fun signOut(): Resource<Boolean> = drinkRemoteDb.signOut()
}