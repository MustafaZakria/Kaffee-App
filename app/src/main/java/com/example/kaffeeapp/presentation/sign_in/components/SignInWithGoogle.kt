package com.example.kaffeeapp.presentation.sign_in.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.repository.SignInWithGoogleResponse

@Composable
fun SignInWithGoogle(
    signInState: State<SignInWithGoogleResponse>,
    onSuccess: () -> Unit,
    onError: (e: Exception) -> Unit,
    onLoading: @Composable () -> Unit
) {
    when (signInState.value) {
        is Resource.Loading -> onLoading.invoke()
        is Resource.Success -> signInState.value.data?.let { signedIn ->
            LaunchedEffect(signedIn) {
                if (signedIn) {
                    onSuccess.invoke()
                }
            }
        }

        is Resource.Failure -> LaunchedEffect(Unit) {
            signInState.value.exception?.let { onError(it) }
        }
    }
}