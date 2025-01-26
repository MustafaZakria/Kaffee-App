package com.example.kaffeeapp.data.local.sharedPreference

import android.content.SharedPreferences
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.util.Constants.EMAIL_KEY
import com.example.kaffeeapp.util.Constants.FAV_DRINKS_KEY
import com.example.kaffeeapp.util.Constants.NAME_KEY
import com.example.kaffeeapp.util.Constants.ORDERS_KEY
import com.example.kaffeeapp.util.Constants.IMAGE_URL_KEY
import com.example.kaffeeapp.util.Constants.POINTS_KEY

class UserSharedPreference (
    private val sharedPreferences: SharedPreferences
) : BaseSharedPreference(sharedPreferences = sharedPreferences) {

    fun addUserInfo(
        user: User
    ) {
        user.apply {
            addString(name, NAME_KEY)
            addString(email, EMAIL_KEY)
            addString(imageUrl, IMAGE_URL_KEY)
            insertList(orders, ORDERS_KEY)
            insertList(favouriteDrinks, FAV_DRINKS_KEY)
            addString(points, POINTS_KEY)
        }
    }

    fun addOrder(id: String) = appendString(id, ORDERS_KEY)

    suspend fun getOrdersIdsFlow() = stringFlow(ORDERS_KEY, "")

    suspend fun getUserNameFlow() = stringFlow(NAME_KEY, "")

    suspend fun getUserEmailFlow() = stringFlow(EMAIL_KEY, "")

    suspend fun getUserPictureFlow() = stringFlow(IMAGE_URL_KEY, "")

    suspend fun getUserPointsFlow() = stringFlow(POINTS_KEY, "0")

    suspend fun getUserFavFlow() = stringFlow(FAV_DRINKS_KEY, "")

    fun setUserPicture(url: String) = addString(url, IMAGE_URL_KEY)

    fun setUserPoints(points: String) = addString(points, POINTS_KEY)

    fun getUserPoints() = sharedPreferences.getString(POINTS_KEY, "0")

    fun getFavDrinksIds() = getList(FAV_DRINKS_KEY)

    fun removeDrink(id: String) {
        val favList = getList(FAV_DRINKS_KEY).toMutableList()
        favList.remove(id)
        insertList(favList, FAV_DRINKS_KEY)
    }

    fun addDrinkToFav(id: String) = appendString(id, FAV_DRINKS_KEY)
}