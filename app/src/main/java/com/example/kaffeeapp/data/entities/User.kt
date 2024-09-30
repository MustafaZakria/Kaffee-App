package com.example.kaffeeapp.data.entities

import com.example.kaffeeapp.util.Constants.EMAIL_KEY
import com.example.kaffeeapp.util.Constants.FAV_DRINKS_KEY
import com.example.kaffeeapp.util.Constants.ID_KEY
import com.example.kaffeeapp.util.Constants.NAME_KEY
import com.example.kaffeeapp.util.Constants.ORDERS_KEY
import com.google.firebase.firestore.DocumentSnapshot

data class User(
    val email: String = "",
    val name: String = "",
    val id: String = "",
    val imageUrl: String = "",
    val favouriteDrinks: List<String> = arrayListOf(),
    val orders: List<String> = arrayListOf()
) {
    companion object {
        fun getFromSnapshot(userSnapshot: DocumentSnapshot?): User {
            val orders = (userSnapshot?.get(ORDERS_KEY) as? List<*>)?.mapNotNull { it as? String }
            val favDrinks =
                (userSnapshot?.get(FAV_DRINKS_KEY) as? List<*>)?.mapNotNull { it as? String }
            return User(
                id = userSnapshot?.get(ID_KEY) as? String ?: "",
                name = userSnapshot?.get(NAME_KEY) as? String ?: "",
                email = userSnapshot?.get(EMAIL_KEY) as? String ?: "",
                orders = orders ?: emptyList(),
                favouriteDrinks = favDrinks ?: emptyList()
            )
        }
    }
}
