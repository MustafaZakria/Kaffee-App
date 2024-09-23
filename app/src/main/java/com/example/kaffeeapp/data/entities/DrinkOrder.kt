package com.example.kaffeeapp.data.entities

data class DrinkOrder(
    val name: String = "",
    val id: String = "",
    val size: String = "",
    val price: String = "",
    val imageUrl: String = "",
    val quantity: Int = 1
)