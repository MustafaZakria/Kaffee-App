package com.example.kaffeeapp.util.model

import com.example.kaffeeapp.data.entities.DeliveryMethod

data class CartDetails(
    val deliveryMethod: DeliveryMethod? = null,
    val isDeliveryEnabled: Boolean = true,
    val isAddressNull: Boolean? = null,
    val isPhoneNumberValid: Boolean? = null,
    val phoneNumberValue: String = ""
)