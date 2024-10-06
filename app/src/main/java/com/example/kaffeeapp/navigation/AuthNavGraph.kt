package com.example.kaffeeapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.kaffeeapp.navigation.model.Graph
import com.example.kaffeeapp.presentation.auth.sign_in.SignInScreen
import com.example.kaffeeapp.presentation.auth.splash.SplashScreen
import com.example.kaffeeapp.util.Constants.SIGNIN_SCREEN
import com.example.kaffeeapp.util.Constants.SPLASH_SCREEN

fun NavGraphBuilder.authNavGraph(
    navHostController: NavHostController
) {
    navigation(
        route = Graph.AuthGraph.route,
        startDestination = AuthScreen.SplashScreen.route
    ) {
        composable(
            AuthScreen.SplashScreen.route
        ) {
            SplashScreen(
                navigateToSignInScreen = {
                    navHostController.popBackStack()
                    navHostController.navigate(AuthScreen.SignInScreen.route)
                },
                navigateToMainGraph = {
                    navHostController.popBackStack()
                    navHostController.navigate(Graph.MainGraph.route)
                }
            )
        }
        composable(
            AuthScreen.SignInScreen.route
        ) {
            SignInScreen(
                navigateToMainScreen = {
                    navHostController.popBackStack()
                    navHostController.navigate(Graph.MainGraph.route)
                }
            )
        }
    }

}

sealed class AuthScreen(val route: String) {
    data object SignInScreen : AuthScreen(SIGNIN_SCREEN)
    data object SplashScreen : AuthScreen(SPLASH_SCREEN)
}