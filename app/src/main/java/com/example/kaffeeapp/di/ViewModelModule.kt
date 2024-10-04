package com.example.kaffeeapp.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.local.DrinkDao
import com.example.kaffeeapp.data.local.sharedPreference.MainSharedPreference
import com.example.kaffeeapp.data.local.sharedPreference.UserSharedPreference
import com.example.kaffeeapp.data.remote.DrinkRemoteDb
import com.example.kaffeeapp.repository.AuthRepositoryImp
import com.example.kaffeeapp.repository.DataRepositoryImp
import com.example.kaffeeapp.repository.MainRepositoryImp
import com.example.kaffeeapp.repository.ProfileRepositoryImp
import com.example.kaffeeapp.repository.interfaces.AuthRepository
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.repository.interfaces.MainRepository
import com.example.kaffeeapp.repository.interfaces.ProfileRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
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
        userSharedPreference: UserSharedPreference,
        mainSharedPreference: MainSharedPreference
    ): MainRepository =
        MainRepositoryImp(drinkDao, db, userSharedPreference, mainSharedPreference)

    @Provides
    fun provideAuthRepo(
        remoteDb: DrinkRemoteDb,
        credentialManager: CredentialManager,
        credentialRequest: GetCredentialRequest
    ): AuthRepository = AuthRepositoryImp(remoteDb, credentialManager, credentialRequest)

    @Provides
    fun provideDataRepository(
        drinkDao: DrinkDao,
        db: DrinkRemoteDb,
        userSharedPreference: UserSharedPreference,
        mainSharedPreference: MainSharedPreference
    ): DataRepository =
        DataRepositoryImp(drinkDao, db, userSharedPreference, mainSharedPreference)

    @Provides
    fun provideProfileRepository(
        remoteDb: DrinkRemoteDb,
        preference: UserSharedPreference
    ): ProfileRepository = ProfileRepositoryImp(remoteDb, preference)
}