package com.example.kaffeeapp.data.entities

import com.google.firebase.firestore.PropertyName

data class Drink (
    val id: Int = 0,
    val name: String = "",
    val imageUrl: String = "",
    val price: DrinkPrice = DrinkPrice(),
    val description: String = "",
    val ingredients: ArrayList<String> = ArrayList(),
    @get:PropertyName("type")
    @set:PropertyName("type")
    var type: DrinkType = DrinkType.HOT
)


enum class DrinkType(val value: String) {
    HOT("hot"),
    COLD("cold");

    companion object {
        fun fromValue(value: String): DrinkType? {
            return entries.find { it.value == value }
        }
    }
}