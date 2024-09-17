package com.example.kaffeeapp.repository

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.repository.interfaces.AuthRepository
import com.example.kaffeeapp.repository.interfaces.RequestCredentialResponse
import com.example.kaffeeapp.repository.interfaces.SignInWithGoogleResponse
import com.example.kaffeeapp.util.Constants.USERS_COLLECTION
import com.example.kaffeeapp.util.model.Resource
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val credentialManager: CredentialManager,
    private val credentialRequest: GetCredentialRequest,
    private val firestore: FirebaseFirestore,
) : AuthRepository {

    override val isUserAuthenticatedInFirebase = firebaseAuth.currentUser != null

    override suspend fun requestSignIn(
        context: Context,
    ): RequestCredentialResponse {
        return try {
            val result = credentialManager.getCredential(
                request = credentialRequest,
                context = context
            )
            Resource.Success(result.credential)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }


    override suspend fun signInWithGoogle(credential: Credential): SignInWithGoogleResponse {
        return try {
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)
                val authCredential =
                    GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
                if (isNewUser) {
                    addUserToFirestore()
                }
                Resource.Success(true)
            } else {
                Resource.Failure(Exception("Received an invalid credential type"))
            }
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun singOut() {
//        credentialManager.clearCredentialState(ClearCredentialStateRequest())
        firebaseAuth.signOut()
    }

    override suspend fun addUserToFirestore() {
        Log.d("users", "reached")
        firebaseAuth.currentUser?.apply {
            firestore.collection(USERS_COLLECTION).document(uid).set(toUser()).await()
        }
    }

    private fun FirebaseUser.toUser(): User {
        return User(
            email = email,
            name = displayName,
            id = uid
        )
    }
}


