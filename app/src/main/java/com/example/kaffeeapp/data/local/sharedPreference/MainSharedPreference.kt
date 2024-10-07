package com.example.kaffeeapp.data.local.sharedPreference

import android.content.SharedPreferences
import android.util.Log
import com.example.kaffeeapp.util.Constants.DARK_MODE_KEY
import com.example.kaffeeapp.util.Constants.PROMO_CODES_KEY

class MainSharedPreference(
    sharedPreferences: SharedPreferences
) : BaseSharedPreference(sharedPreferences = sharedPreferences) {

    fun insertPromoCodes(promoCodes: Map<String, String>) {
        val promoString = promoCodes.entries.joinToString(",") { "${it.key}:${it.value}" }
        super.addString(promoString, PROMO_CODES_KEY)
    }

    fun getPromoCodes(): Map<String, String> {
        val promoCodes = getString(PROMO_CODES_KEY)
        return promoCodes.split(",").associate {
            val (key, value) = it.split(":")
            key.trim() to value.trim()
        }
    }

    fun changeSystemMode() {
        val oldValue = getBoolean(DARK_MODE_KEY, false)

        addBoolean(!oldValue, DARK_MODE_KEY)
    }

    suspend fun isSystemOnDarkModeFlow() = booleanFlow(DARK_MODE_KEY, false)

    fun getIsSystemOnDarkMode() = getBoolean(DARK_MODE_KEY, false)
}