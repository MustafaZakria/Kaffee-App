package com.example.kaffeeapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.kaffeeapp.data.local.DrinkDatabase
import com.example.kaffeeapp.data.local.sharedPreference.MainSharedPreference
import com.example.kaffeeapp.data.local.sharedPreference.UserSharedPreference
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.util.Constants.DRINK_DATABASE_NAME
import com.example.kaffeeapp.util.Constants.SHARED_PREFERENCE_NAME
import com.example.kaffeeapp.util.DispatcherProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideFirestoreInstance(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideFirebaseStorage(): FirebaseStorage = Firebase.storage

    @Singleton
    @Provides
    fun provideFusedLocationProvider(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

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
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,
        firebaseStorage: FirebaseStorage
    ) = DrinkRemoteDb(firestore, firebaseAuth, firebaseStorage)


    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

    @Singleton
    @Provides
    fun provideSharedPreference(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)


    @Singleton
    @Provides
    fun provideUserSharedPref(
        sharedPreference: SharedPreferences
    ): UserSharedPreference = UserSharedPreference(sharedPreference)

    @Singleton
    @Provides
    fun provideMainSharedPref(
        sharedPreference: SharedPreferences
    ): MainSharedPreference = MainSharedPreference(sharedPreference)
}