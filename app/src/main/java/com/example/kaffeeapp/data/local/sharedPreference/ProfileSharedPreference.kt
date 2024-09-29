package com.example.kaffeeapp.data.local.sharedPreference

import android.content.SharedPreferences
import com.example.kaffeeapp.util.Constants.EMAIL_KEY
import com.example.kaffeeapp.util.Constants.NAME_KEY
import com.example.kaffeeapp.util.Constants.ORDERS_KEY
import com.example.kaffeeapp.util.Constants.IMAGE_URL_KEY

class ProfileSharedPreference (
    sharedPreferences: SharedPreferences
) : BaseSharedPreference(sharedPreferences = sharedPreferences) {

    fun insertOrdersList(orders: List<String>) = insertList(orders, ORDERS_KEY)
    fun getOrdersIds() = getList(ORDERS_KEY)
    fun addOrder(id: String) = appendString(id, ORDERS_KEY)


    fun addUserInfo(
        name: String,
        email: String,
        imageUrl: String
    ) {
        addString(name, NAME_KEY)
        addString(email, EMAIL_KEY)
        addString(imageUrl, IMAGE_URL_KEY)
    }

    fun getUserName() = getString(NAME_KEY)

    fun getUserEmail() = getString(EMAIL_KEY)

    fun getUserPicture() = getString(IMAGE_URL_KEY)
}