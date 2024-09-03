package com.example.kaffeeapp.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.kaffeeapp.R
import com.example.kaffeeapp.repository.AuthRepositoryImp
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideGoogleIdOption(
        @ApplicationContext context: Context
    ): GetGoogleIdOption = GetGoogleIdOption
        .Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(context.getString(R.string.web_client_id))
        .setAutoSelectEnabled(false)
        .build()

    @Provides
    fun provideCredentialRequest(
        option: GetGoogleIdOption
    ): GetCredentialRequest = GetCredentialRequest.Builder().addCredentialOption(option).build()

    @Provides
    @Singleton
    fun provideCredentialManager(
        @ApplicationContext context: Context
    ): CredentialManager = CredentialManager.create(context)

    @Provides
    @Singleton
    fun provideAuthRepo(
        auth: FirebaseAuth,
        credentialManager: CredentialManager,
        credentialRequest: GetCredentialRequest
    ): AuthRepositoryImp = AuthRepositoryImp(auth, credentialManager, credentialRequest)

}