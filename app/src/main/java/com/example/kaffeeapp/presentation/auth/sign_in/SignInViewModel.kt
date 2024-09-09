package com.example.kaffeeapp.presentation.auth.sign_in

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.Credential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.repository.interfaces.AuthRepository
import com.example.kaffeeapp.repository.interfaces.RequestCredentialResponse
import com.example.kaffeeapp.repository.interfaces.SignInWithGoogleResponse
import com.example.kaffeeapp.util.model.Resource.Loading
import com.example.kaffeeapp.util.model.Resource.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _signInRequest = mutableStateOf<RequestCredentialResponse>(Success(null))
    val signInRequest: State<RequestCredentialResponse> = _signInRequest


    private val _signInWithGoogleResponse = mutableStateOf<SignInWithGoogleResponse>(Success(false))
    val signInWithGoogleResponse: State<SignInWithGoogleResponse> = _signInWithGoogleResponse

    fun requestSignIn(context: Context) = viewModelScope.launch {
        _signInRequest.value = Loading()
        _signInRequest.value = repo.requestSignIn(context)
    }

    fun signInWithGoogle(credential: Credential) = viewModelScope.launch {
        _signInWithGoogleResponse.value = Loading()
        _signInWithGoogleResponse.value = repo.signInWithGoogle(credential)
    }

    fun getAddGoogleAccountIntent(): Intent {
        val intent = Intent(Settings.ACTION_ADD_ACCOUNT)
        intent.putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
        return intent
    }
}