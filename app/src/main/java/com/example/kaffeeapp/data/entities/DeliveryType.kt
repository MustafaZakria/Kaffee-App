package com.example.kaffeeapp.data.entities

import com.example.kaffeeapp.util.Constants.ADDRESS
import com.example.kaffeeapp.util.Constants.BRANCH_ADDRESS
import com.example.kaffeeapp.util.Constants.LATITUDE
import com.example.kaffeeapp.util.Constants.LONGITUDE

sealed class DeliveryType {
    data class HomeDelivery(
        val address: String?,
        val latitude: Double?,
        val longitude: Double?
    ) : DeliveryType()

    data class BranchDelivery(val branchAddress: String?) : DeliveryType()

    fun toMap(): Map<String, String> {
        return if (this is HomeDelivery) {
            mapOf(
                ADDRESS to this.address.toString(),
                LATITUDE to this.latitude.toString(),
                LONGITUDE to this.longitude.toString()
            )
        } else {
            mapOf(
                BRANCH_ADDRESS to (this as BranchDelivery).branchAddress.toString()
            )
        }
    }

}