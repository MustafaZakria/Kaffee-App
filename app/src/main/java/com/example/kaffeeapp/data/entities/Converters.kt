package com.example.kaffeeapp.data.entities

import androidx.room.TypeConverter
import java.util.Locale

class Converters {

    @TypeConverter
    fun fromDrinkType(type: DrinkType): String {
        return type.name.lowercase(Locale.ROOT)
    }

    @TypeConverter
    fun toDrinkType(value: String): DrinkType {
        return DrinkType.fromValue(value)
    }

    @TypeConverter
    fun toPriceMap(value: String): Map<String, String> = value.split(",").associate {
        val (key, value) = it.split(":")
        key.trim() to value.trim()
    }

    @TypeConverter
    fun fromPriceMap(map: Map<String, String>): String =
        map.entries.joinToString(",") { "${it.key}:${it.value}" }

    @TypeConverter
    fun toIngredientsList(value: String): List<String> = ArrayList(value.split(","))

    @TypeConverter
    fun fromIngredientsList(list: List<String>): String = list.joinToString(",")
}