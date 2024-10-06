package com.example.kaffeeapp.util.model

import com.example.kaffeeapp.util.Constants.KEY_LARGE_SIZE
import com.example.kaffeeapp.util.Constants.KEY_MEDIUM_SIZE
import com.example.kaffeeapp.util.Constants.KEY_SMALL_SIZE
import com.example.kaffeeapp.util.Constants.LARGE_SHORTENED
import com.example.kaffeeapp.util.Constants.MEDIUM_SHORTENED
import com.example.kaffeeapp.util.Constants.SMALL_SHORTENED

enum class DrinkSize(val key: String, val shortened: String) {
    SMALL(KEY_SMALL_SIZE, SMALL_SHORTENED),
    MEDIUM(KEY_MEDIUM_SIZE, MEDIUM_SHORTENED),
    LARGE(KEY_LARGE_SIZE, LARGE_SHORTENED)
}