package com.example.kaffeeapp.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme

@Composable
fun ProgressBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            color = Color.LightGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KaffeeAppTheme {
        ProgressBar()
    }
}