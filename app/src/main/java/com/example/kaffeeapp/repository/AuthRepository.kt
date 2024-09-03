package com.example.kaffeeapp.repository

import android.content.Context
import androidx.credentials.Credential
import com.example.kaffeeapp.model.Resource
import com.google.firebase.auth.AuthResult


typealias SignInWithGoogleResponse = Resource<Boolean>
typealias RequestCredentialResponse = Resource<Credential>

interface AuthRepository {

    suspend fun requestSignIn(
        context: Context,
//        filterByAuthorizedAccounts: Boolean
    ): RequestCredentialResponse

    suspend fun signInWithGoogle(credential: Credential): SignInWithGoogleResponse

}