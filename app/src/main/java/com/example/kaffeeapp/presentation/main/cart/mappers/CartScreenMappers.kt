package com.example.kaffeeapp.presentation.main.cart.mappers

object CartScreenMappers {
    fun String.getDiscountValueBy(discountPercent: String): String {
        val discount = discountPercent.toDoubleOrNull() ?: 0.0
        val itemsPrice = this.toDoubleOrNull() ?: 0.0
        return (discount * itemsPrice).toString()
    }
}