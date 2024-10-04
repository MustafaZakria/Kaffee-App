package com.example.kaffeeapp.data.entities

import com.example.kaffeeapp.util.Constants.ADDRESS_KEY
import com.example.kaffeeapp.util.Constants.LATITUDE_KEY
import com.example.kaffeeapp.util.Constants.LONGITUDE_KEY
import com.example.kaffeeapp.util.Constants.NAME_KEY

sealed class DeliveryType {
    data class HomeDelivery(
        val address: String?,
        val latitude: Double?,
        val longitude: Double?
    ) : DeliveryType()

    data class BranchDelivery(
        val branchName: String?,
        val branchAddress: String?,
    ) : DeliveryType()

    fun toMap(): Map<String, String> {
        return if (this is HomeDelivery) {
            mapOf(
                ADDRESS_KEY to this.address.toString(),
                LATITUDE_KEY to this.latitude.toString(),
                LONGITUDE_KEY to this.longitude.toString()
            )
        } else {
            mapOf(
                NAME_KEY to (this as BranchDelivery).branchName.toString(),
                ADDRESS_KEY to this.branchAddress.toString()
            )
        }
    }

}