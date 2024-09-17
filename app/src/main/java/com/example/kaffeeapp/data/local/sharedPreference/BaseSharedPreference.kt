package com.example.kaffeeapp.data.local.sharedPreference

import android.content.SharedPreferences
import com.example.kaffeeapp.util.Constants.FAV_DRINKS_KEY

open class BaseSharedPreference(
    private val sharedPreferences: SharedPreferences
) {

    protected fun insertList(list: List<String>, key: String) {
        val idsString = list.joinToString(",")
        sharedPreferences.edit().putString(key, idsString).apply()
    }

    protected fun getList(key: String): List<String> {
        val idsString = sharedPreferences.getString(FAV_DRINKS_KEY, "")
        return idsString?.let {
            ArrayList(it.split(","))
        } ?: emptyList()
    }

    protected fun addString(string: String, key: String) {
        val favList = getList(key).toMutableList()
        favList.add(string)
        insertList(favList, key)
    }
}