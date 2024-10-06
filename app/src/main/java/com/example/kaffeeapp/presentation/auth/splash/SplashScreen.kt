package com.example.kaffeeapp.presentation.auth.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.auth.sign_in.SignInViewModel
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToSignInScreen: () -> Unit,
    navigateToMainGraph: () -> Unit
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.coffee_anim))
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = composition) {
        composition?.let {
            val delay = it.duration
            delay(delay.toLong())
            visible = true // change visibility to true after delay
            delay(1000) // Add additional delay to keep the text visible
            CoroutineScope(Dispatchers.Main).launch {
                if (viewModel.isUserAuthenticated) {
                    navigateToMainGraph.invoke()
                } else {
                    navigateToSignInScreen.invoke()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center // Center items in the Box
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .size(200.dp)
        )

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
            modifier = Modifier.padding(top = 120.dp)
        ) {
            CustomizedText(
                text = stringResource(id = R.string.app_name),
                fontSize = dimensionResource(id = R.dimen.text_size_x_large),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center) // Center the text over the animation
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KaffeeAppTheme {
//        SplashScreen() {}
    }
}
