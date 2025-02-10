package com.example.kaffeeapp.presentation.auth.sign_in

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.Credential
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.presentation.auth.sign_in.components.GoogleButton
import com.example.kaffeeapp.presentation.auth.sign_in.components.GradientBackground.gradientBackground
import com.example.kaffeeapp.presentation.auth.sign_in.components.RequestSignIn
import com.example.kaffeeapp.presentation.auth.sign_in.components.SignInWithGoogle
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.util.Constants.NETWORK_ERROR
import com.example.kaffeeapp.util.Constants.SIGNED_IN_SUCCESSFULLY
import com.example.kaffeeapp.util.Fonts.sora
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.util.snackbarStuff.SnackbarController
import com.example.kaffeeapp.util.snackbarStuff.SnackbarEvent
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToMainScreen: () -> Unit
) {

    val context = LocalContext.current
    val signInState by viewModel.signInWithGoogleResponse
    val requestState by viewModel.signInRequest


    SignInScreenContent(
        signInState = signInState,
        requestState = requestState,
        navigateToMainScreen = { navigateToMainScreen.invoke() },
        requestSignIn = { viewModel.requestSignIn(context) },
        signInWithGoogle = { cred -> viewModel.signInWithGoogle(cred) },
        addGoogleAccountIntent = viewModel.getAddGoogleAccountIntent()
    )
}

@Composable
fun SignInScreenContent(
    signInState: Resource<Boolean>,
    requestState: Resource<Credential>,
    navigateToMainScreen: () -> Unit,
    requestSignIn: () -> Unit,
    signInWithGoogle: (Credential) -> Unit,
    addGoogleAccountIntent: Intent
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                requestSignIn.invoke()
            }
        }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground(listOf(Color.Black, MaterialTheme.colorScheme.secondary), 45f)
    ) {
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(R.drawable.home_background),
                contentDescription = stringResource(id = R.string.coffee_img_desc),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Box(
            modifier = Modifier
                .gradientBackground(
                    listOf(
                        Color.Black,
                        Color.Black,
                        Color.Transparent,
                        Color.Transparent
                    ), 90f
                ),
            contentAlignment = Alignment.Center
        ) {}
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 28.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = stringResource(id = R.string.home_heading_1),
                    style = MaterialTheme.typography.displaySmall,
                    fontFamily = sora,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.home_heading_2),
                    style = MaterialTheme.typography.titleSmall,
                    fontFamily = sora,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(25.dp))
                GoogleButton {
                    requestSignIn.invoke()
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }


    RequestSignIn(
        requestState = requestState,
        onSuccess = { cred ->
            signInWithGoogle.invoke(cred)
        },
        onError = { exception ->
            if (exception is NoCredentialException) {
                launcher.launch(addGoogleAccountIntent)
            }
        }) {
        ProgressBar(modifier = Modifier.fillMaxSize())
    }

    val scope = rememberCoroutineScope()

    SignInWithGoogle(
        signInState = signInState,
        onSuccess = {
            scope.launch {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = SIGNED_IN_SUCCESSFULLY
                    )
                )
            }
            navigateToMainScreen.invoke()
        },
        onError = { exception ->
            scope.launch {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = exception.message ?: NETWORK_ERROR
                    )
                )
            }
        }) {
        ProgressBar(modifier = Modifier.fillMaxSize())
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KaffeeAppTheme {
        SignInScreenContent(
            signInState = Resource.Loading(),
            requestState = Resource.Loading(),
            navigateToMainScreen = { },
            requestSignIn = { },
            signInWithGoogle = {},
            addGoogleAccountIntent = Intent()
        )
    }
}