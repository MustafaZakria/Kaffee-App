package com.example.kaffeeapp.data.local.sharedPreference

import android.content.SharedPreferences

open class BaseSharedPreference(
    private val sharedPreferences: SharedPreferences
) {

    protected fun insertList(list: List<String>, key: String) {
        val idsString = list.joinToString(",")
        sharedPreferences.edit().putString(key, idsString).apply()
    }

    protected fun getList(key: String): List<String> {
        val idsString = sharedPreferences.getString(key, "")
        return idsString?.let {
            ArrayList(it.split(","))
        } ?: emptyList()
    }

    protected fun appendString(string: String, key: String) {
        val favList = getList(key).toMutableList()
        favList.add(string)
        insertList(favList, key)
    }

    protected fun addString(string: String, key: String) {
        sharedPreferences.edit().putString(key, string).apply()
    }

    protected fun getString(key: String): String {
        return sharedPreferences.getString(key, "").toString()
    }
}