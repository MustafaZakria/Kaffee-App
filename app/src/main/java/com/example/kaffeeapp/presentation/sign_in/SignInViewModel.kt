package com.example.kaffeeapp.presentation.sign_in

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.Credential
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.model.Resource
import com.example.kaffeeapp.model.Resource.Loading
import com.example.kaffeeapp.model.Resource.Success
import com.example.kaffeeapp.repository.AuthRepositoryImp
import com.example.kaffeeapp.repository.RequestCredentialResponse
import com.example.kaffeeapp.repository.SignInWithGoogleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: AuthRepositoryImp
) : ViewModel() {

    private val _signInRequest = mutableStateOf<RequestCredentialResponse>(Success(null))
    val signInRequest: State<RequestCredentialResponse> = _signInRequest


    private val _signInWithGoogleResponse = mutableStateOf<SignInWithGoogleResponse>(Success(false))
    val signInWithGoogleResponse: State<SignInWithGoogleResponse> = _signInWithGoogleResponse

    fun requestSignIn(context: Context) = viewModelScope.launch {
        _signInRequest.value = Loading()
        when (val result = repo.requestSignIn(context)) {
            is Resource.Failure -> {
                if (result.exception is NoCredentialException && _signInRequest.value is Success) {
                    _signInRequest.value = repo.requestSignIn(context, false)
                } else {
                    //we can here send a failure of intent to handle creating new account
                }
            }
            else -> {
                _signInRequest.value = result
            }
        }
    }

    fun signInWithGoogle(credential: Credential) = viewModelScope.launch {
        _signInWithGoogleResponse.value = Loading()
        _signInWithGoogleResponse.value = repo.signInWithGoogle(credential)
    }
}