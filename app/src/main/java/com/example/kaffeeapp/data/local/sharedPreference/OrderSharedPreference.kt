package com.example.kaffeeapp.data.local.sharedPreference

import android.content.SharedPreferences
import com.example.kaffeeapp.util.Constants.ORDERS_KEY

class OrderSharedPreference (
    sharedPreferences: SharedPreferences
) : BaseSharedPreference(sharedPreferences = sharedPreferences) {

    private val key = ORDERS_KEY

    fun insertOrdersList(orders: List<String>) = insertList(orders, key)
    fun getOrdersIds() = getList(key)
    fun addOrder(id: String) = appendString(id, key)
}