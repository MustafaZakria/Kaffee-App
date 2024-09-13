package com.example.kaffeeapp.presentation.main.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kaffeeapp.presentation.auth.sign_in.components.GradientBackground.gradientBackground
import com.example.kaffeeapp.ui.theme.bannerColorCenter
import com.example.kaffeeapp.ui.theme.bannerColorEnd
import com.example.kaffeeapp.ui.theme.bannerColorStart

@Composable
fun BackgroundBanner() {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .height(maxHeight * 0.3f)
                .fillMaxWidth()
                .gradientBackground(
                    listOf(
                        MaterialTheme.colorScheme.bannerColorStart,
                        MaterialTheme.colorScheme.bannerColorCenter,
                        MaterialTheme.colorScheme.bannerColorEnd
                    ), 240f
                )
        ) {}
    }
}