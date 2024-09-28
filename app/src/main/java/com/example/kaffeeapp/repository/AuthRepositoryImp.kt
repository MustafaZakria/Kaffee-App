package com.example.kaffeeapp.repository

import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.repository.interfaces.AuthRepository
import com.example.kaffeeapp.repository.interfaces.RequestCredentialResponse
import com.example.kaffeeapp.repository.interfaces.SignInWithGoogleResponse
import com.example.kaffeeapp.util.model.Resource
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImp @Inject constructor(
    private val remoteDb: DrinkRemoteDb,
    private val credentialManager: CredentialManager,
    private val credentialRequest: GetCredentialRequest
) : AuthRepository {

    override val isUserAuthenticatedInFirebase = remoteDb.isUserAuthenticated

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
        return if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential =
                GoogleIdTokenCredential.createFrom(credential.data)
            val authCredential =
                GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
            val response = remoteDb.signInWithCredential(authCredential)

            response
        } else {
            Resource.Failure(Exception("Received an invalid credential type"))
        }
    }

    override fun singOut(): Resource<Boolean> = remoteDb.signOut()
}


