package com.example.kaffeeapp.navigation

import com.example.kaffeeapp.util.Constants.MAIN_SCREEN
import com.example.kaffeeapp.util.Constants.SIGNIN_SCREEN
import com.example.kaffeeapp.util.Constants.SPLASH_SCREEN

sealed class Screen(val route: String) {
    data object SignInScreen : Screen(SIGNIN_SCREEN)
    data object SplashScreen : Screen(SPLASH_SCREEN)
    data object MainScreen : Screen(MAIN_SCREEN)
}