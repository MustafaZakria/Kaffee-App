package com.example.kaffeeapp.presentation.sign_in

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.Credential
import com.example.kaffeeapp.R
import com.example.kaffeeapp.model.Resource
import com.example.kaffeeapp.repository.RequestCredentialResponse
import com.example.kaffeeapp.repository.SignInWithGoogleResponse
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme

@Composable
fun SignInScreen(
    viewModel: SignInViewModel?
) {
    val isDarkTheme = isSystemInDarkTheme()
    val context = LocalContext.current
    val onSignInClick = {
        viewModel?.requestSignIn(context)
        Log.d("***", "pressed")
    }
    val signInState = viewModel?.signInWithGoogleResponse
    val requestState = viewModel?.signInRequest


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Surface(
            shape = CircleShape,
            color = when (isDarkTheme) {
                true -> Color(0xFF131314)
                false -> Color(0xFFFFFFFF)
            },
            modifier = Modifier
                .height(50.dp)
                .width(260.dp)
                .clip(CircleShape)
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = when (isDarkTheme) {
                            true -> Color(0xFF8E918F)
                            false -> Color.Transparent
                        }
                    ),
                    shape = CircleShape
                )
                .clickable { onSignInClick() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 5.dp)
            ) {
//                Spacer(modifier = Modifier.width(14.dp))
                Image(
                    painterResource(id = R.drawable.g_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 5.dp)
//                        .size(40.dp)
                )
//                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Continue with Google", modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 5.dp)
                )
//                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

    if (requestState != null) {
        OnRequestSignIn(
            requestState,
            onSuccess = { cred -> viewModel.signInWithGoogle(cred) }
        ) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    if (signInState != null) {
        SignInWithGoogle(
            signInState,
            onSuccess = {
                Toast.makeText(context, "Successfully!", Toast.LENGTH_SHORT).show()
            }
        ) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun SignInWithGoogle(
    signInState: State<SignInWithGoogleResponse>,
    onSuccess: () -> Unit,
    onError: (message: String) -> Unit
) {
    when (signInState.value) {
        is Resource.Loading -> Unit
        is Resource.Success -> signInState.value.data?.let { signedIn ->
            if (signedIn) {
                onSuccess.invoke()
            }
        }

        is Resource.Failure -> LaunchedEffect(key1 = Unit) {
            onError(signInState.value.exception?.message.toString())
        }
    }
}

@Composable
fun OnRequestSignIn(
    requestState: State<RequestCredentialResponse>,
    onSuccess: (credential: Credential) -> Unit,
    onError: (message: String) -> Unit
) {
    when (requestState.value) {
        is Resource.Loading -> Unit
        is Resource.Success -> requestState.value.data?.let { cred ->
            LaunchedEffect(key1 = Unit) {
                onSuccess(cred)
            }
        }

        is Resource.Failure -> LaunchedEffect(key1 = Unit) {
            onError(requestState.value.exception?.message.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KaffeeAppTheme {
        SignInScreen(null)
    }
}