package com.example.kaffeeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kaffeeapp.presentation.sign_in.SignInScreen
import com.example.kaffeeapp.presentation.splash.SplashScreen


@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(
            Screen.SplashScreen.route
        ) {
            SplashScreen {
                navController.navigate(Screen.SignInScreen.route)
            }
        }

        composable(
            Screen.SignInScreen.route
        ) {
            SignInScreen()
        }
    }
}