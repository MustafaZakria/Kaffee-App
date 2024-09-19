package com.example.kaffeeapp.data.local.sharedPreference

import android.content.SharedPreferences
import com.example.kaffeeapp.util.Constants.FAV_DRINKS_KEY

class DrinkSharedPreference(
    sharedPreferences: SharedPreferences
) : BaseSharedPreference(sharedPreferences = sharedPreferences) {

    private val key = FAV_DRINKS_KEY

    fun removeDrink(id: String) {
        val favList = getList(key).toMutableList()
        favList.remove(id)
        insertList(favList, key)
    }

    fun insertFavDrinksList(ids: List<String>) = insertList(ids, key)

    fun getFavDrinksIds() = getList(key)

    fun addDrinkToFav(id: String) = addString(id, key)

    fun isDrinkFav(id: String): Boolean = getFavDrinksIds().contains(id)

}