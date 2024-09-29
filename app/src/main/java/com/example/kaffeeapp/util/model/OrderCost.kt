package com.example.kaffeeapp.util.model

data class OrderCost(
    val itemsPrice: String = "0.0",
    val discountValue: String = "0.0",
    val deliveryFee: String = "0.0"
) {
    fun getTotalCost(): String {
        val itemsPrice = itemsPrice.toDoubleOrNull() ?: 0.0
        val discountValue = discountValue.toDoubleOrNull() ?: 0.0
        val deliveryFee = deliveryFee.toDoubleOrNull() ?: 0.0

        return (itemsPrice + deliveryFee - discountValue).toString()
    }
}
