package com.example.kaffeeapp.data.entities

data class Order(
    val orderId: String = "",
    val uid: String = "",
    val timestamp: Long = 0L,
    val telephoneNumber: String = "",
    val isHomeDeliveryOrder: Boolean = true,
    val totalPrice: String = "",

    val deliveryDetails: Map<String, String> = mapOf(),

    val drinkOrders: List<DrinkOrder> = listOf()
)