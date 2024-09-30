package com.example.kaffeeapp.repository.interfaces

import com.example.kaffeeapp.data.entities.Order
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.util.model.Resource

typealias OrdersResponse = Resource<List<Order>>

interface ProfileRepository {
    suspend fun getAllResources(): OrdersResponse

    fun getUser(): User
}