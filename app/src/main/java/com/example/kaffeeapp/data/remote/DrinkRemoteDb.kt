package com.example.kaffeeapp.data.remote

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
            val querySnap = drinkCollection.get().await()

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
                    ingredients = (drinkData["ingredients"] as? List<*>)?.mapNotNull { it as? String }
                        ?: emptyList(),
                    price = price,
                    type = DrinkType.fromValue(drinkData["type"] as? String ?: DrinkType.HOT.name)
                )
                drinkList.add(drink)
            }

            Resource.Success(drinkList)
        } catch (e: Exception) {

            Resource.Failure(e)
        }
    }
}