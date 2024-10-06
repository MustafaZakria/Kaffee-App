package com.example.kaffeeapp.presentation.main.cart.models

import com.example.kaffeeapp.data.entities.DeliveryType
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.util.model.DrinkSize

data class CartDetails(
    val deliveryValue: DeliveryType? = null,
    val isDeliveryEnabled: Boolean = true,
    val phoneNumberValue: String = "",
    val promoCodeValue: String = "",
    val note: String = "",
    val drinkOrders: MutableList<DrinkOrder> = mutableListOf()
) {
    fun setOrderQuantity(index: Int, quantity: Int) {
        val newDrinkOrder = drinkOrders[index].copy(
            quantity = quantity
        )
        drinkOrders[index] = newDrinkOrder
    }

    fun removeOrder(orderIndex: Int) {
        drinkOrders.removeAt(orderIndex)
    }

    fun calculateOrdersCost(): String {
        return drinkOrders.sumOf { it.quantity * (it.price.toDoubleOrNull() ?: 0.0) }.toString()
    }

    fun addOrder(drink: Drink, size: DrinkSize) {
        drink.apply {
            val price = price[size.key] ?: "0"
            drinkOrders.add(
                DrinkOrder(
                    name = name,
                    id = id,
                    size = size.shortened,
                    price = price,
                    imageUrl = imageUrl,
                    quantity = 1
                )
            )
        }
    }
}