package com.example.kaffeeapp.data.entities

import com.example.kaffeeapp.util.Constants.ADDRESS_KEY
import com.example.kaffeeapp.util.Constants.LATITUDE_KEY
import com.example.kaffeeapp.util.Constants.LONGITUDE_KEY
import com.example.kaffeeapp.util.Constants.NAME_KEY
import com.example.kaffeeapp.util.Constants.WORK_HOURS_KEY
import com.google.firebase.firestore.DocumentSnapshot

data class BranchDetails(
    val name: String = "",
    val address: String = "",
    val workHours: String = "",
    val latitude: String = "",
    val longitude: String = "",
) {
    companion object {
        fun getFromSnapshot(orderSnapshot: DocumentSnapshot?): BranchDetails {
            return BranchDetails(
                name = orderSnapshot?.get(NAME_KEY) as? String ?: "",
                address = orderSnapshot?.get(ADDRESS_KEY) as? String ?: "",
                workHours = orderSnapshot?.get(WORK_HOURS_KEY) as? String ?: "",
                latitude = orderSnapshot?.get(LATITUDE_KEY) as? String ?: "",
                longitude = orderSnapshot?.get(LONGITUDE_KEY) as? String ?: "",
            )
        }
    }
}
