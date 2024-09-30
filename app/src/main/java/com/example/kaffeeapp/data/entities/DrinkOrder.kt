package com.example.kaffeeapp.data.entities

data class DrinkOrder(
    val name: String = "",
    val id: String = "",
    val size: String = "",
    val price: String = "",
    val imageUrl: String = "",
    val quantity: Int = 1
) {
    companion object {

        fun getFromMap(drinkMap: Map<String, Any>): DrinkOrder {
            return DrinkOrder(
                name = drinkMap["name"] as? String ?: "",
                id = drinkMap["id"] as? String ?: "",
                size = drinkMap["size"] as? String ?: "",
                price = drinkMap["price"] as? String ?: "",
                imageUrl = drinkMap["imageUrl"] as? String ?: "",
                quantity = (drinkMap["quantity"] as? Number)?.toInt() ?: 1
            )
        }
    }
}