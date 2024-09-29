package com.example.kaffeeapp.data.remote

import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkType
import com.example.kaffeeapp.data.entities.Order
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.repository.interfaces.SignInWithGoogleResponse
import com.example.kaffeeapp.util.Constants.DESCRIPTION_KEY
import com.example.kaffeeapp.util.Constants.DRINKS_COLLECTION
import com.example.kaffeeapp.util.Constants.EMAIL_KEY
import com.example.kaffeeapp.util.Constants.FAV_DRINKS_KEY
import com.example.kaffeeapp.util.Constants.ID_KEY
import com.example.kaffeeapp.util.Constants.IMAGE_URL_KEY
import com.example.kaffeeapp.util.Constants.INGREDIENTS_KEY
import com.example.kaffeeapp.util.Constants.NAME_KEY
import com.example.kaffeeapp.util.Constants.ORDERS_COLLECTION
import com.example.kaffeeapp.util.Constants.ORDERS_KEY
import com.example.kaffeeapp.util.Constants.PRICE_KEY
import com.example.kaffeeapp.util.Constants.RATING_KEY
import com.example.kaffeeapp.util.Constants.TYPE_KEY
import com.example.kaffeeapp.util.Constants.USERS_COLLECTION
import com.example.kaffeeapp.util.Utils.toUser
import com.example.kaffeeapp.util.model.Resource
import com.google.firebase.auth.AuthCredential
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
    var isUserAuthenticated = false
        private set

    init {
        firebaseAuth.addAuthStateListener { auth ->
            currentUserId = auth.currentUser?.uid
            isUserAuthenticated = currentUserId != null
        }
    }

    suspend fun getAllDrinks(): DrinksResponse {
        return try {
            val drinkList = mutableListOf<Drink>()
            val querySnap = firestore.collection(DRINKS_COLLECTION).get().await()

            querySnap.forEach { document ->
                val drinkData = document.data

                val price = (drinkData[PRICE_KEY] as? Map<*, *>)?.let { priceMap ->
                    priceMap.mapKeys { it.key as String }
                        .mapValues { it.value as String }
                } ?: emptyMap()

                val drink = Drink(
                    id = drinkData[ID_KEY] as? String ?: "",
                    name = drinkData[NAME_KEY] as? String ?: "",
                    imageUrl = drinkData[IMAGE_URL_KEY] as? String ?: "",
                    description = drinkData[DESCRIPTION_KEY] as? String ?: "",
                    rating = drinkData[RATING_KEY] as? String ?: "",
                    ingredients = (drinkData[INGREDIENTS_KEY] as? List<*>)?.mapNotNull { it as? String }
                        ?: emptyList(),
                    price = price,
                    type = DrinkType.fromValue(drinkData[TYPE_KEY] as? String ?: "")
                )
                drinkList.add(drink)
            }
            Resource.Success(drinkList)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }


    suspend fun addFavDrink(id: String): Resource<Boolean> {
        return try {
            currentUserId?.let {
                firestore.collection(USERS_COLLECTION).document(it)
                    .update(FAV_DRINKS_KEY, FieldValue.arrayUnion(id)).await()
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    suspend fun removeFavDrink(id: String): Resource<Boolean> {
        return try {
            currentUserId?.let {
                firestore.collection(USERS_COLLECTION).document(it)
                    .update(FAV_DRINKS_KEY, FieldValue.arrayRemove(id)).await()
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }


    suspend fun getUser(): Resource<User> {
        return try {
            val userSnapshot = currentUserId?.let {
                firestore.collection(USERS_COLLECTION).document(it).get().await()
            }
            val orders = (userSnapshot?.get(ORDERS_KEY) as? List<*>)?.mapNotNull { it as? String }
            val favDrinks = (userSnapshot?.get(FAV_DRINKS_KEY) as? List<*>)?.mapNotNull { it as? String }
            val user = User(
                id = userSnapshot?.get(ID_KEY) as? String ?: "",
                name = userSnapshot?.get(NAME_KEY) as? String ?: "",
                email = userSnapshot?.get(EMAIL_KEY) as? String ?: "",
                orders = orders ?: emptyList(),
                favouriteDrinks = favDrinks ?: emptyList()
            )
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    suspend fun addOrderToServer(order: Order): Resource<Boolean> {
        return try {
            order.setUserId(currentUserId ?: "")
            firestore.collection(ORDERS_COLLECTION).document(order.orderId)
                .set(order).await()

            currentUserId?.let {
                firestore.collection(USERS_COLLECTION).document(it)
                    .update(ORDERS_KEY, FieldValue.arrayUnion(order.orderId)).await()
            }

            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }


    fun signOut(): Resource<Boolean> {
        return try {
            firebaseAuth.signOut()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    suspend fun signInWithCredential(authCredential: AuthCredential): SignInWithGoogleResponse {
        return try {
            val authResult = firebaseAuth.signInWithCredential(authCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                firebaseAuth.currentUser?.let {
                    firestore.collection(USERS_COLLECTION).document(it.uid).set(it.toUser()).await()
                }
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}