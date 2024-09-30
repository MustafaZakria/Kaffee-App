package com.example.kaffeeapp.repository

import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.data.local.sharedPreference.ProfileSharedPreference
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.repository.interfaces.OrdersResponse
import com.example.kaffeeapp.repository.interfaces.ProfileRepository
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImp(
    private val drinkRemoteDb: DrinkRemoteDb,
    private val profileSharedPreference: ProfileSharedPreference
) : ProfileRepository {

    override suspend fun getAllResources(): OrdersResponse = drinkRemoteDb.getOrders()

    override fun getUser() =
        User(
            name = profileSharedPreference.getUserName(),
            email = profileSharedPreference.getUserEmail(),
            imageUrl = profileSharedPreference.getUserPicture(),
            orders = profileSharedPreference.getOrdersIds()
        )
}