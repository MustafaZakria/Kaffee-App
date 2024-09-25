package com.example.kaffeeapp.data.entities

import com.example.kaffeeapp.util.Constants.ADDRESS
import com.example.kaffeeapp.util.Constants.BRANCH_ADDRESS
import com.example.kaffeeapp.util.Constants.LATITUDE
import com.example.kaffeeapp.util.Constants.LONGITUDE
import com.example.kaffeeapp.data.entities.DeliveryMethod.BranchDelivery
import com.example.kaffeeapp.data.entities.DeliveryMethod.AddressDelivery
import com.google.firebase.Timestamp

data class Order(
    val id: String = "",
    val uid: String = "",
    val timestamp: Long = 0L,
    val telephoneNumber: String = "",
    val isHomeDeliveryOrder: Boolean = true,
    val totalPrice: String = "",

    val deliveryMethod: DeliveryMethod? = null,

    val drinkOrders: List<DrinkOrder> = listOf()
) {
    fun getDeliveryDetailsMap(): Map<String, String> {
        return if(deliveryMethod is AddressDelivery) {
            mapOf(
                ADDRESS to deliveryMethod.address.toString(),
                LATITUDE to deliveryMethod.latitude.toString(),
                LONGITUDE to deliveryMethod.longitude.toString()
            )
        } else {
            mapOf(
                BRANCH_ADDRESS to (deliveryMethod as BranchDelivery).branchAddress.toString()
            )
        }
    }
}