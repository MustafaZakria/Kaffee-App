package com.example.kaffeeapp.repository.interfaces

import android.net.Uri
import com.example.kaffeeapp.data.entities.Order
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.util.model.Resource
import kotlinx.coroutines.flow.Flow

typealias OrdersResponse = Resource<List<Order>>

interface ProfileRepository {
    suspend fun getAllResources(): Flow<OrdersResponse>

    suspend fun getUser(): Flow<User>

    suspend fun setUserImage(uri: Uri?): Resource<String>
    suspend fun isSystemOnDarkMode(): Flow<Boolean>
    fun changeSystemMode()
    fun getIsSystemOnDarkMode(): Boolean
}