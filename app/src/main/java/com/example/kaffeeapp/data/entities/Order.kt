package com.example.kaffeeapp.data.entities

import com.example.kaffeeapp.util.Constants.DELIVERY_DETAILS_KEY
import com.example.kaffeeapp.util.Constants.DRINK_ORDERS_LIST_KEY
import com.example.kaffeeapp.util.Constants.IS_HOME_DELIVERY_KEY
import com.example.kaffeeapp.util.Constants.NOTE_KEY
import com.example.kaffeeapp.util.Constants.ORDER_ID_KEY
import com.example.kaffeeapp.util.Constants.PHONE_NUMBER_KEY
import com.example.kaffeeapp.util.Constants.TIMESTAMP_KEY
import com.example.kaffeeapp.util.Constants.TOTAL_PRICE_KEY
import com.example.kaffeeapp.util.Constants.UID_KEY
import com.google.firebase.firestore.DocumentSnapshot
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class Order(
    val orderId: String = "",
    var uid: String = "",
    val timestamp: Long = 0L,
    val telephoneNumber: String = "",
    val isHomeDeliveryOrder: Boolean = true,
    val totalPrice: String = "",
    val note: String = "",
    val deliveryDetails: Map<String, String> = mapOf(),
    val drinkOrders: List<DrinkOrder> = listOf()
) {
    fun setUserId(id: String) {
        uid = id
    }

    fun getFormattedDate(): String {
        val calendar = Calendar.getInstance().apply {
            this.timeInMillis = timestamp
        }
        val dateFormat = SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    companion object {
        fun getFromSnapshot(orderSnapshot: DocumentSnapshot?): Order {
            val deliverDetails =
                (orderSnapshot?.get(DELIVERY_DETAILS_KEY) as? Map<*, *>)?.let { map ->
                    map.mapKeys { it.key as String }
                        .mapValues { it.value as String }
                }
            val drinkOrders = (orderSnapshot?.get(DRINK_ORDERS_LIST_KEY) as? List<Map<String, Any>>)?.mapNotNull { drinkMap ->
                DrinkOrder.getFromMap(drinkMap)
            }
            return Order(
                orderId = orderSnapshot?.get(ORDER_ID_KEY) as? String ?: "",
                uid = orderSnapshot?.get(UID_KEY) as? String ?: "",
                timestamp = orderSnapshot?.get(TIMESTAMP_KEY) as? Long ?: 0L,
                telephoneNumber = orderSnapshot?.get(PHONE_NUMBER_KEY) as? String ?: "",
                isHomeDeliveryOrder = orderSnapshot?.get(IS_HOME_DELIVERY_KEY) as? Boolean ?: true,
                totalPrice = orderSnapshot?.get(TOTAL_PRICE_KEY) as? String ?: "",
                note = orderSnapshot?.get(NOTE_KEY) as? String ?: "",
                deliveryDetails = deliverDetails ?: mapOf(),
                drinkOrders = drinkOrders ?: listOf()
            )
        }
    }
}