package com.example.kaffeeapp.util

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import java.util.UUID

object Utils {

    @OptIn(ExperimentalMaterial3Api::class)
    val clearRippleConfiguration = RippleConfiguration(
        color = Color.Transparent, rippleAlpha = RippleAlpha(
            draggedAlpha = 0.0f,
            focusedAlpha = 0.0f,
            hoveredAlpha = 0.0f,
            pressedAlpha = 0.0f,
        )
    )

    fun generateUniqueId(): String {
        return UUID.randomUUID().toString()
    }

}