package com.example.kaffeeapp.data.remote

import com.example.kaffeeapp.data.entities.Drink
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrinkApi @Inject constructor(
    private val drinkCollection: CollectionReference
) {
    suspend fun getAllDrinks(): List<Drink> {
        return try {
            drinkCollection.get().await().toObjects(Drink::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}