package com.example.kaffeeapp.data.entities

import com.example.kaffeeapp.util.Constants.ADDRESS
import com.example.kaffeeapp.util.Constants.BRANCH_ADDRESS
import com.example.kaffeeapp.util.Constants.LATITUDE
import com.example.kaffeeapp.util.Constants.LONGITUDE

sealed class DeliveryMethod {
    data class AddressDelivery(
        val address: String?,
        val latitude: Double?,
        val longitude: Double?
    ) : DeliveryMethod()

    data class BranchDelivery(val branchAddress: String?) : DeliveryMethod()

    fun toMap(): Map<String, String> {
        return if (this is AddressDelivery) {
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