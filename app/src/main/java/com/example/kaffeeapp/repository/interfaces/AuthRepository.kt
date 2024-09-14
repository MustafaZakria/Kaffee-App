package com.example.kaffeeapp.repository.interfaces

import android.content.Context
import androidx.credentials.Credential
import com.example.kaffeeapp.util.model.Resource

typealias SignInWithGoogleResponse = Resource<Boolean>
typealias RequestCredentialResponse = Resource<Credential>

interface AuthRepository {

    suspend fun requestSignIn(
        context: Context
    ): RequestCredentialResponse

    suspend fun signInWithGoogle(credential: Credential): SignInWithGoogleResponse

    suspend fun singOut()
}