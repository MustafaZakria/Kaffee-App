package com.example.kaffeeapp.data.remote

import android.util.Log
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkType
import com.example.kaffeeapp.util.Constants.DRINKS_COLLECTION
import com.example.kaffeeapp.util.Constants.FAV_DRINKS_KEY
import com.example.kaffeeapp.util.Constants.ORDERS_KEY
import com.example.kaffeeapp.util.Constants.USERS_COLLECTION
import com.example.kaffeeapp.util.model.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

typealias DrinksResponse = Resource<List<Drink>>

@Singleton
class DrinkRemoteDb @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    private var currentUserId: String? = null

    init {
        firebaseAuth.addAuthStateListener { auth ->
            currentUserId = auth.currentUser?.uid
        }
    }

    suspend fun getAllDrinks(): DrinksResponse {
        return try {
            val drinkList = mutableListOf<Drink>()
            val querySnap = firestore.collection(DRINKS_COLLECTION).get().await()

            querySnap.forEach { document ->
                val drinkData = document.data

                val price = (drinkData["price"] as? Map<*, *>)?.let { priceMap ->
                    priceMap.mapKeys { it.key as String }
                        .mapValues { it.value as String }
                } ?: emptyMap()

                val drink = Drink(
                    id = drinkData["id"] as? String ?: "",
                    name = drinkData["name"] as? String ?: "",
                    imageUrl = drinkData["imageUrl"] as? String ?: "",
                    description = drinkData["description"] as? String ?: "",
                    rating = drinkData["rating"] as? String ?: "",
                    ingredients = (drinkData["ingredients"] as? List<*>)?.mapNotNull { it as? String }
                        ?: emptyList(),
                    price = price,
                    type = DrinkType.fromValue(drinkData["type"] as? String ?: "")
                )
                drinkList.add(drink)
            }

            Resource.Success(drinkList)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    suspend fun getFavDrinksIds(): List<String> {
        return try {
            val user = currentUserId?.let {
                firestore.collection(USERS_COLLECTION).document(it).get().await()
            }
            (user?.get(FAV_DRINKS_KEY) as? List<*>)?.mapNotNull { it as? String } ?: emptyList()
        } catch (e: Exception) {
            Log.e("Exception(FAV DRINKS):", e.message.toString())
            emptyList()
        }
    }

    suspend fun addFavDrink(id: String) {
        try {
            currentUserId?.let {
                firestore.collection(USERS_COLLECTION).document(it)
                    .update(FAV_DRINKS_KEY, FieldValue.arrayUnion(id)).await()
            }
        } catch (e: Exception) {
            Log.e("Exception(FAV DRINKS):", e.message.toString())
        }
    }

    suspend fun removeFavDrink(id: String) {
        try {
            currentUserId?.let {
                firestore.collection(USERS_COLLECTION).document(it)
                    .update(FAV_DRINKS_KEY, FieldValue.arrayRemove(id)).await()
            }
        } catch (e: Exception) {
            Log.e("Exception(FAV DRINKS):", e.message.toString())
        }
    }

    suspend fun getOrdersIds(): List<String> {
        return try {
            val user = currentUserId?.let {
                firestore.collection(USERS_COLLECTION).document(it).get().await()
            }
            (user?.get(ORDERS_KEY) as? List<*>)?.mapNotNull { it as? String } ?: emptyList()
        } catch (e: Exception) {
            Log.e("Exception(ORDERS):", e.message.toString())
            emptyList()
        }
    }

    suspend fun addOrderToUser(id: String) {
        try {
            currentUserId?.let {
                firestore.collection(USERS_COLLECTION).document(it)
                    .update(ORDERS_KEY, FieldValue.arrayUnion(id)).await()
            }
        } catch (e: Exception) {
            Log.e("Exception(ORDERS):", e.message.toString())
        }
    }
}