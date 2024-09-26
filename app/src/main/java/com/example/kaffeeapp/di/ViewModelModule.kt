package com.example.kaffeeapp.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.local.DrinkDao
import com.example.kaffeeapp.data.local.sharedPreference.DrinkSharedPreference
import com.example.kaffeeapp.data.local.sharedPreference.OrderSharedPreference
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.kaffeeapp.repository.AuthRepositoryImp
import com.example.kaffeeapp.repository.DataRepositoryImp
import com.example.kaffeeapp.repository.MainRepositoryImp
import com.example.kaffeeapp.repository.interfaces.AuthRepository
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.repository.interfaces.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {
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
    fun provideCredentialManager(
        @ApplicationContext context: Context
    ): CredentialManager = CredentialManager.create(context)

    @Provides
    fun provideMainRepository(
        drinkDao: DrinkDao,
        db: DrinkRemoteDb,
        drinkSharedPreference: DrinkSharedPreference,
        orderSharedPreference: OrderSharedPreference
    ): MainRepository =
        MainRepositoryImp(drinkDao, db, drinkSharedPreference, orderSharedPreference)

    @Provides
    fun provideAuthRepo(
        auth: FirebaseAuth,
        credentialManager: CredentialManager,
        credentialRequest: GetCredentialRequest,
        firestore: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImp(auth, credentialManager, credentialRequest, firestore)

    @Provides
    fun provideDataRepository(
        drinkDao: DrinkDao,
        db: DrinkRemoteDb,
        drinkSharedPreference: DrinkSharedPreference,
        orderSharedPreference: OrderSharedPreference,
        firebaseAuth: FirebaseAuth
    ): DataRepository =
        DataRepositoryImp(drinkDao, db, drinkSharedPreference, orderSharedPreference, firebaseAuth)
}