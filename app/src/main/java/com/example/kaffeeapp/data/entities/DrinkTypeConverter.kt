package com.example.kaffeeapp.data.entities

import com.google.firebase.firestore.PropertyName

class DrinkTypeConverter {

    @PropertyName("type")
    fun toFirestore(type: DrinkType): String {
        return type.value
    }

    @PropertyName("type")
    fun fromFirestore(value: String): DrinkType {
        return DrinkType.fromValue(value) ?: DrinkType.HOT
    }
}