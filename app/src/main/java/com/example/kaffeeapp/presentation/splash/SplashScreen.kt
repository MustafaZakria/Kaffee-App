package com.example.kaffeeapp.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaffeeapp.R
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.ui.theme.accentColor

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.accentColor),
        contentAlignment = Alignment.Center
    ) {
        // Replace with your logo/image
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KaffeeAppTheme {
        SplashScreen()
    }
}
