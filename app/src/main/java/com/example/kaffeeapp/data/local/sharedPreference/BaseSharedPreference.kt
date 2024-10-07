package com.example.kaffeeapp.data.local.sharedPreference

import android.content.SharedPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

open class BaseSharedPreference(
    private val sharedPreferences: SharedPreferences
) {

    protected fun insertList(list: List<String>, key: String) {
        val idsString = list.joinToString(",")
        sharedPreferences.edit().putString(key, idsString).apply()
    }

    protected fun getList(key: String): List<String> {
        val idsString = sharedPreferences.getString(key, "")
        return idsString?.split(",") ?: emptyList()
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

    protected fun addBoolean(bool: Boolean, key: String) {
        sharedPreferences.edit().putBoolean(key, bool).apply()
    }

    protected fun getBoolean(key: String, default: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }

    protected suspend fun stringFlow(key: String, defaultValue: String): Flow<String> = callbackFlow {
        val initialValue = sharedPreferences.getString(key, defaultValue) ?: defaultValue
        trySend(initialValue)

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key) {
                val newValue = sharedPreferences.getString(key, defaultValue) ?: defaultValue
                trySend(newValue)  // Emit the new value whenever it changes
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    protected suspend fun booleanFlow(key: String, defaultValue: Boolean): Flow<Boolean> = callbackFlow {
        val initialValue = sharedPreferences.getBoolean(key, defaultValue)
        trySend(initialValue)

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key) {
                val newValue = sharedPreferences.getBoolean(key, defaultValue)
                trySend(newValue)  // Emit the new value whenever it changes
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }
}