package com.example.kaffeeapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kaffeeapp.util.Constants.DESCRIPTION_KEY
import com.example.kaffeeapp.util.Constants.ID_KEY
import com.example.kaffeeapp.util.Constants.IMAGE_URL_KEY
import com.example.kaffeeapp.util.Constants.INGREDIENTS_KEY
import com.example.kaffeeapp.util.Constants.NAME_KEY
import com.example.kaffeeapp.util.Constants.PRICE_KEY
import com.example.kaffeeapp.util.Constants.RATING_KEY
import com.example.kaffeeapp.util.Constants.TYPE_KEY
import com.google.firebase.firestore.DocumentSnapshot

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

    companion object {
        fun getFromSnapshot(drinkSnapshot: DocumentSnapshot?): Drink {
            val price = (drinkSnapshot?.get(PRICE_KEY) as? Map<*, *>)?.let { priceMap ->
                priceMap.mapKeys { it.key as String }
                    .mapValues { it.value as String }
            }
            return Drink(
                id = drinkSnapshot?.get(ID_KEY) as? String ?: "",
                name = drinkSnapshot?.get(NAME_KEY) as? String ?: "",
                imageUrl = drinkSnapshot?.get(IMAGE_URL_KEY) as? String ?: "",
                description = drinkSnapshot?.get(DESCRIPTION_KEY) as? String ?: "",
                rating = drinkSnapshot?.get(RATING_KEY) as? String ?: "",
                ingredients = (drinkSnapshot?.get(INGREDIENTS_KEY) as? List<*>)?.mapNotNull { it as? String }
                    ?: emptyList(),
                price = price ?: emptyMap(),
                type = DrinkType.fromValue(drinkSnapshot?.get(TYPE_KEY) as? String ?: "")
            )
        }
    }
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