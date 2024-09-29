package com.example.kaffeeapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drinks")
data class Drink(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val price: Map<String, String> = mapOf(),
    val description: String = "",
    val ingredients: List<String> = arrayListOf(),
    val type: DrinkType = DrinkType.HOT,
    val rating: String = ""
) {
    fun getFormattedIngredients() = ingredients.joinToString(" | ")

}


enum class DrinkType {
    HOT,
    COLD;

    companion object {
        fun fromValue(value: String): DrinkType {
            return try {
                valueOf(value.uppercase())
            } catch (e: Exception) {
                HOT
            }
        }
    }
}