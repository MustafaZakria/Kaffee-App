package com.example.kaffeeapp.data.entities

sealed class DeliveryMethod {
    data class AddressDelivery(val address: String, val latitude: Double, val longitude: Double): DeliveryMethod()
    data class BranchDelivery(val branchAddress: String): DeliveryMethod()
}