package com.example.kaffeeapp.data.remote

import android.util.Log
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkType
import com.example.kaffeeapp.util.model.Resource
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

typealias DrinksResponse = Resource<List<Drink>>

@Singleton
class DrinkRemoteDb @Inject constructor(
    private val drinkCollection: CollectionReference
) {

    suspend fun getAllDrinks(): DrinksResponse {
        return try {
            val drinkList = mutableListOf<Drink>()
            drinkCollection.get().addOnSuccessListener {
                it.forEach { document ->
                    val drinkData = document.data
                    val drink = Drink(
                        id = drinkData["id"] as? String ?: "",
                        name = drinkData["name"] as? String ?: "",
                        imageUrl = drinkData["imageUrl"] as? String ?: "",
                        description = drinkData["description"] as? String ?: "",
                        ingredients = drinkData["ingredients"] as? List<String> ?: emptyList(),
                        price = drinkData["price"] as? Map<String, String> ?: emptyMap(),
                        type = DrinkType.fromValue(drinkData["type"] as? String ?: DrinkType.HOT.name)
                    )
                    drinkList.add(drink)
                }
            }.await()
            Resource.Success(drinkList)
        } catch (e: Exception) {

            Resource.Failure(e)
        }
    }
}