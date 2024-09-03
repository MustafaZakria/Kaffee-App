package com.example.kaffeeapp.presentation.sign_in

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.presentation.sign_in.components.GoogleButton
import com.example.kaffeeapp.presentation.sign_in.components.GradientBackground.gradientBackground
import com.example.kaffeeapp.presentation.sign_in.components.RequestSignIn
import com.example.kaffeeapp.presentation.sign_in.components.SignInWithGoogle
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.util.Fonts.sora
import com.google.android.gms.common.api.ApiException

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
//    navigateToProductsScreen: () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()
    val context = LocalContext.current
    val signInState = viewModel.signInWithGoogleResponse
    val requestState = viewModel.signInRequest

    val density = LocalDensity.current.density
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val largeDensitySize = (13 * density) * (screenWidth / 360f) // Scale based on width
    val smallDensitySize = (6 * density) * (screenWidth / 360f) // Scale based on width

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    viewModel.requestSignIn(context)
                } catch (it: ApiException) {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground(listOf(Color.Black, Color.White), 45f)
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.TopCenter
        ) {
            val availableHeight = maxHeight
            Image(
                painter = painterResource(R.drawable.main_background),
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(availableHeight * 0.7f)
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
                    style = MaterialTheme.typography.displayMedium,
                    fontSize = largeDensitySize.sp,
                    fontFamily = sora,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.home_heading_2),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = smallDensitySize.sp,
                    fontFamily = sora,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(30.dp))
                GoogleButton(isDarkTheme) {
                    viewModel?.requestSignIn(context)
                }
                Spacer(modifier = Modifier.height(smallDensitySize.dp))
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
        ProgressBar()
    }



    SignInWithGoogle(
        signInState = signInState,
        onSuccess = {
//                navigateToProductsScreen.invoke()
        },
        onError = { exception ->
            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
        }) {
        ProgressBar()
    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KaffeeAppTheme {
        SignInScreen()
    }
}