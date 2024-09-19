package com.example.kaffeeapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.kaffeeapp.data.local.DrinkDatabase
import com.example.kaffeeapp.data.local.sharedPreference.DrinkSharedPreference
import com.example.kaffeeapp.data.local.sharedPreference.OrderSharedPreference
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.util.Constants.DRINK_DATABASE_NAME
import com.example.kaffeeapp.util.Constants.SHARED_PREFERENCE_NAME
import com.example.kaffeeapp.util.DispatcherProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideFirestoreInstance(): FirebaseFirestore = Firebase.firestore

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
        firebaseAuth: FirebaseAuth
    ) = DrinkRemoteDb(firestore, firebaseAuth)


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
    fun provideDrinkSharedPref(
        sharedPreference: SharedPreferences
    ): DrinkSharedPreference = DrinkSharedPreference(sharedPreference)

    @Singleton
    @Provides
    fun provideOrderSharedPref(
        sharedPreference: SharedPreferences
    ): OrderSharedPreference = OrderSharedPreference(sharedPreference)


}