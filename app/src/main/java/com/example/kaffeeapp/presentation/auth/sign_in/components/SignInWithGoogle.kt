package com.example.kaffeeapp.presentation.auth.sign_in.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.kaffeeapp.repository.interfaces.SignInWithGoogleResponse
import com.example.kaffeeapp.util.model.Resource

@Composable
fun SignInWithGoogle(
    signInState: SignInWithGoogleResponse,
    onSuccess: () -> Unit,
    onError: (e: Exception) -> Unit,
    onLoading: @Composable () -> Unit
) {
    when (signInState) {
        is Resource.Loading -> onLoading.invoke()
        is Resource.Success -> signInState.data?.let { signedIn ->
            LaunchedEffect(signedIn) {
                if (signedIn) {
                    onSuccess.invoke()
                }
            }
        }

        is Resource.Failure -> LaunchedEffect(Unit) {
            signInState.exception?.let { onError(it) }
        }
    }
}