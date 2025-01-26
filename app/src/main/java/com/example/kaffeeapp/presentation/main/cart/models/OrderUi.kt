package com.example.kaffeeapp.presentation.main.cart.models

import com.example.kaffeeapp.data.entities.DeliveryType
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.data.entities.Order
import com.example.kaffeeapp.util.Utils.generateUniqueId
import com.example.kaffeeapp.util.model.DrinkSize

data class OrderUi(
    val deliveryValue: DeliveryType? = null,
    val isDeliveryEnabled: Boolean = true,
    val phoneNumberValue: String = "",
    val promoCodeValue: String = "",
    val note: String = "",
    val itemsPrice: String = "0.0",
    val discountValue: String = "0.0",
    val deliveryFee: String = "0.0",
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

    fun getTotalCost(): String {
        val itemsPrice = itemsPrice.toDoubleOrNull() ?: 0.0
        val discountValue = discountValue.toDoubleOrNull() ?: 0.0
        val deliveryFee = deliveryFee.toDoubleOrNull() ?: 0.0

        return (itemsPrice + deliveryFee - discountValue).toString()
    }

    fun toOrder() = Order(
        orderId = generateUniqueId(),
        timestamp = System.currentTimeMillis(),
        telephoneNumber = phoneNumberValue,
        isHomeDeliveryOrder = isDeliveryEnabled,
        totalPrice = getTotalCost(),
        deliveryDetails = deliveryValue?.toMap() ?: mapOf(),
        drinkOrders = drinkOrders,
        note = note
    )

}