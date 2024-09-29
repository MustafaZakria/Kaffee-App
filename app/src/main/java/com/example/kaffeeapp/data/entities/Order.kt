package com.example.kaffeeapp.data.entities

data class Order(
    val orderId: String = "",
    var uid: String = "",
    val timestamp: Long = 0L,
    val telephoneNumber: String = "",
    val isHomeDeliveryOrder: Boolean = true,
    val totalPrice: String = "",
    val note: String = "",

    val deliveryDetails: Map<String, String> = mapOf(),

    val drinkOrders: List<DrinkOrder> = listOf()
) {
    fun setUserId(id: String) {
        uid = id
    }
}