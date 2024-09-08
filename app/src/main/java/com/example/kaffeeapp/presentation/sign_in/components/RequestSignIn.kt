package com.example.kaffeeapp.presentation.sign_in.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.credentials.Credential
import com.example.kaffeeapp.repository.interfaces.RequestCredentialResponse
import com.example.kaffeeapp.util.model.Resource

@Composable
fun RequestSignIn(
    requestState: State<RequestCredentialResponse>,
    onSuccess: (credential: Credential) -> Unit,
    onError: (e: Exception) -> Unit,
    onLoading: @Composable () -> Unit
) {
    when (requestState.value) {
        is Resource.Loading -> onLoading.invoke()
        is Resource.Success -> requestState.value.data?.let { cred ->
            LaunchedEffect(cred) {
                onSuccess(cred)
            }
        }

        is Resource.Failure -> LaunchedEffect(Unit) {
            requestState.value.exception?.let { onError(it) }
        }
    }
}