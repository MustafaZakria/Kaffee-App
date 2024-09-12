package com.example.kaffeeapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName

@Entity(tableName = "drinks")
data class Drink(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val price: Map<String, String> = mapOf(),
    val description: String = "",
    val ingredients: List<String> = arrayListOf(),
    @get:PropertyName("type")
    @set:PropertyName("type")
    var type: DrinkType = DrinkType.HOT
)


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