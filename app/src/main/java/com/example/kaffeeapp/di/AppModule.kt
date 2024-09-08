package com.example.kaffeeapp.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.room.Room
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.local.DrinkDatabase
import com.example.kaffeeapp.data.local.DrinkDao
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.repository.AuthRepositoryImp
import com.example.kaffeeapp.repository.interfaces.AuthRepository
import com.example.kaffeeapp.repository.interfaces.MainRepository
import com.example.kaffeeapp.repository.MainRepositoryImp
import com.example.kaffeeapp.util.Constants.DRINK_COLLECTION
import com.example.kaffeeapp.util.Constants.DRINK_DATABASE_NAME
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
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
    @Singleton
    fun provideFirestoreInstance(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideDrinkCollection(
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection(DRINK_COLLECTION)

    @Provides
    @Singleton
    fun provideGoogleIdOption(
        @ApplicationContext context: Context
    ): GetGoogleIdOption = GetGoogleIdOption
        .Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(context.getString(R.string.web_client_id))
        .setAutoSelectEnabled(false)
        .build()

    @Provides
    @Singleton
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
    ): AuthRepository = AuthRepositoryImp(auth, credentialManager, credentialRequest)

    @Provides
    @Singleton
    fun provideDrinkDatabase(
        @ApplicationContext context: Context
    ): DrinkDatabase = Room.databaseBuilder(
        context,
        DrinkDatabase::class.java,
        DRINK_DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideDrinkDao(db: DrinkDatabase) = db.getDrinkDao()

    @Provides
    @Singleton
    fun provideDrinkRemoteDb(
        collectionReference: CollectionReference
    ) = DrinkRemoteDb(collectionReference)

    @Singleton
    @Provides
    fun provideMainRepository(
        drinkDao: DrinkDao,
        db: DrinkRemoteDb
    ): MainRepository = MainRepositoryImp(drinkDao, db)
}