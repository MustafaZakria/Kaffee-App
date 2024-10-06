package com.example.kaffeeapp.repository

import android.net.Uri
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.data.local.sharedPreference.UserSharedPreference
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.repository.interfaces.OrdersResponse
import com.example.kaffeeapp.repository.interfaces.ProfileRepository
import com.example.kaffeeapp.util.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImp(
    private val drinkRemoteDb: DrinkRemoteDb,
    private val userSharedPreference: UserSharedPreference
) : ProfileRepository {

    override suspend fun getAllResources(): Flow<OrdersResponse> = flow {
        emit(Resource.Loading())
        val response = drinkRemoteDb.getOrders()
        emit(response)
    }

    override suspend fun getUser(): Flow<User> =
        combine(
            userSharedPreference.getUserNameFlow(),
            userSharedPreference.getUserEmailFlow(),
            userSharedPreference.getUserPictureFlow(),
            userSharedPreference.getOrdersIdsFlow(),
            userSharedPreference.getUserPointsFlow(),
        ) { name, email, imageUrl, orders, points ->
            User(
                name = name,
                email = email,
                imageUrl = imageUrl,
                orders = orders.split(","),
                points = points)
        }


    override suspend fun setUserImage(uri: Uri?): Resource<String> {
        val uploadingResponse = uri?.let { drinkRemoteDb.uploadUserImage(it) }
        if(uploadingResponse is Resource.Success) {
            val url = uploadingResponse.data ?: ""
            userSharedPreference.setUserPicture(url)
            return Resource.Success(url)
        }
        return Resource.Failure(uploadingResponse?.exception)
    }
}