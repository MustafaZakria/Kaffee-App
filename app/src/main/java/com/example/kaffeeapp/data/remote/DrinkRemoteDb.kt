package com.example.kaffeeapp.data.remote

import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.Order
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.repository.interfaces.OrdersResponse
import com.example.kaffeeapp.repository.interfaces.SignInWithGoogleResponse
import com.example.kaffeeapp.util.Constants.DRINKS_COLLECTION
import com.example.kaffeeapp.util.Constants.FAV_DRINKS_KEY
import com.example.kaffeeapp.util.Constants.ORDERS_COLLECTION
import com.example.kaffeeapp.util.Constants.ORDERS_KEY
import com.example.kaffeeapp.util.Constants.UID_KEY
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
            querySnap.documents.forEach { document ->
                drinkList.add(Drink.getFromSnapshot(document))
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
            val user = User.getFromSnapshot(userSnapshot)
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

    suspend fun getOrders(): OrdersResponse {
        return try {
            val orders: MutableList<Order> = mutableListOf()
            val orderSnapshots = firestore.collection(ORDERS_COLLECTION)
                .whereEqualTo(UID_KEY, currentUserId).get().await()

            orderSnapshots.documents.forEach { orderSnapshot ->
                orders.add(Order.getFromSnapshot(orderSnapshot))
            }
            Resource.Success(orders)
        } catch (e: Exception) {
            Resource.Failure(e)
        }

    }
}