package com.example.kaffeeapp.data.local.sharedPreference

import android.content.SharedPreferences
import com.example.kaffeeapp.util.Constants.EMAIL_KEY
import com.example.kaffeeapp.util.Constants.FAV_DRINKS_KEY
import com.example.kaffeeapp.util.Constants.NAME_KEY
import com.example.kaffeeapp.util.Constants.ORDERS_KEY
import com.example.kaffeeapp.util.Constants.IMAGE_URL_KEY

class UserSharedPreference (
    sharedPreferences: SharedPreferences
) : BaseSharedPreference(sharedPreferences = sharedPreferences) {

    fun addUserInfo(
        name: String,
        email: String,
        imageUrl: String,
        orders: List<String>,
        favourites: List<String>
    ) {
        addString(name, NAME_KEY)
        addString(email, EMAIL_KEY)
        addString(imageUrl, IMAGE_URL_KEY)
        insertList(orders, ORDERS_KEY)
        insertList(favourites, FAV_DRINKS_KEY)
    }

    fun getOrdersIds() = getList(ORDERS_KEY)

    fun addOrder(id: String) = appendString(id, ORDERS_KEY)

    fun getUserName() = getString(NAME_KEY)

    fun getUserEmail() = getString(EMAIL_KEY)

    fun getUserPicture() = getString(IMAGE_URL_KEY)

    fun setUserPicture(url: String) = addString(url, IMAGE_URL_KEY)

    fun removeDrink(id: String) {
        val favList = getList(FAV_DRINKS_KEY).toMutableList()
        favList.remove(id)
        insertList(favList, FAV_DRINKS_KEY)
    }

    fun getFavDrinksIds() = getList(FAV_DRINKS_KEY)

    fun addDrinkToFav(id: String) = appendString(id, FAV_DRINKS_KEY)
}