package com.example.kaffeeapp.data.entities

data class User(
    val email: String? = "",
    val name: String? = "",
    val id: String? = "",
    val favDrinks: List<String?> = arrayListOf(),
    val orders: List<String?> = arrayListOf()
)
