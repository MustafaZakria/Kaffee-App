package com.example.kaffeeapp.presentation.auth.sign_in

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.presentation.auth.sign_in.components.GoogleButton
import com.example.kaffeeapp.presentation.auth.sign_in.components.GradientBackground.gradientBackground
import com.example.kaffeeapp.presentation.auth.sign_in.components.RequestSignIn
import com.example.kaffeeapp.presentation.auth.sign_in.components.SignInWithGoogle
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.util.Fonts.sora

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToMainScreen: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        if(viewModel.isUserAuthenticated) {
            navigateToMainScreen.invoke()
        }
    }

    val isDarkTheme = isSystemInDarkTheme()
    val context = LocalContext.current
    val signInState = viewModel.signInWithGoogleResponse
    val requestState = viewModel.signInRequest

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.requestSignIn(context)
            }
        }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground(listOf(Color.Black, MaterialTheme.colorScheme.secondary), 45f)
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(R.drawable.main_background),
                contentDescription = stringResource(id = R.string.coffee_img_desc),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxHeight * 0.7f)
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
                GoogleButton(
                    isDarkTheme
                ) {
                    viewModel.requestSignIn(context)
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }


    RequestSignIn(
        requestState = requestState,
        onSuccess = { cred ->
            viewModel.signInWithGoogle(cred)
        },
        onError = { exception ->
            if (exception is NoCredentialException) {
                val intent = viewModel.getAddGoogleAccountIntent()
                launcher.launch(intent)
            }
        }) {
        ProgressBar(modifier = Modifier.fillMaxSize())
    }



    SignInWithGoogle(
        signInState = signInState,
        onSuccess = {
            navigateToMainScreen.invoke()
        },
        onError = { exception ->
            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
        }) {
        ProgressBar(modifier = Modifier.fillMaxSize())
    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KaffeeAppTheme {
        SignInScreen() {}
    }
}