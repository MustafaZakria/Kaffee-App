package com.example.kaffeeapp.repository

import android.net.Uri
import androidx.collection.emptyIntSet
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.data.local.sharedPreference.ProfileSharedPreference
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.repository.interfaces.OrdersResponse
import com.example.kaffeeapp.repository.interfaces.ProfileRepository
import com.example.kaffeeapp.util.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImp(
    private val drinkRemoteDb: DrinkRemoteDb,
    private val profileSharedPreference: ProfileSharedPreference
) : ProfileRepository {

    override suspend fun getAllResources(): Flow<OrdersResponse> = flow {
        emit(Resource.Loading())
        val response = drinkRemoteDb.getOrders()
        emit(response)
    }

    override fun getUser(): Flow<User> = flow {
        val user = User(
            name = profileSharedPreference.getUserName(),
            email = profileSharedPreference.getUserEmail(),
            imageUrl = profileSharedPreference.getUserPicture(),
            orders = profileSharedPreference.getOrdersIds()
        )
        emit(user)
    }

    override suspend fun setUserImage(uri: Uri?): Resource<String> {
        val uploadingResponse = uri?.let { drinkRemoteDb.uploadUserImage(it) }
        if(uploadingResponse is Resource.Success) {
            val url = uploadingResponse.data ?: ""
            profileSharedPreference.setUserPicture(url)
            return Resource.Success(url)
        }
        return Resource.Failure(uploadingResponse?.exception)
    }
}