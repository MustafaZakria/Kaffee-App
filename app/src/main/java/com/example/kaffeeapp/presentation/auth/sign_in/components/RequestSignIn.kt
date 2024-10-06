package com.example.kaffeeapp.presentation.auth.sign_in.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.credentials.Credential
import com.example.kaffeeapp.repository.interfaces.RequestCredentialResponse
import com.example.kaffeeapp.util.model.Resource

@Composable
fun RequestSignIn(
    requestState: RequestCredentialResponse,
    onSuccess: (credential: Credential) -> Unit,
    onError: (e: Exception) -> Unit,
    onLoading: @Composable () -> Unit
) {
    when (requestState) {
        is Resource.Loading -> onLoading.invoke()
        is Resource.Success -> requestState.data?.let { cred ->
            LaunchedEffect(cred) {
                onSuccess(cred)
            }
        }

        is Resource.Failure -> LaunchedEffect(Unit) {
            requestState.exception?.let { onError(it) }
        }
    }
}