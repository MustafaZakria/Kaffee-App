package com.example.kaffeeapp.util

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RippleConfiguration
import androidx.compose.ui.graphics.Color
import com.example.kaffeeapp.data.entities.DeliveryType
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.navigation.MainScreen
import com.example.kaffeeapp.navigation.model.bottomNavItems
import com.example.kaffeeapp.util.Constants.INVALID_VALUE
import com.example.kaffeeapp.util.Constants.NULL_VALUE
import com.google.firebase.auth.FirebaseUser
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

    fun FirebaseUser.toUser(): User {
        return User(
            email = email ?: "",
            name = displayName ?: "",
            id = uid
        )
    }

    fun showBadgeOnCart() {
        bottomNavItems.first { it.route == MainScreen.CartScreen.route }.hasUpdate.value = true
    }

    fun removeBadgeOnCart() {
        bottomNavItems.first { it.route == MainScreen.CartScreen.route }.hasUpdate.value = false
    }
}