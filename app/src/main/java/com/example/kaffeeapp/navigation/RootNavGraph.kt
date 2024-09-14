package com.example.kaffeeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kaffeeapp.presentation.main.MainScreen

@Composable
fun RootNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Graph.MainGraph.route,
        route = Graph.RootGraph.route
    ) {
        authNavGraph(navHostController = navHostController)
        composable(route = Graph.MainGraph.route) {
            MainScreen(
                logout = {
                    navHostController.navigate(AuthScreen.SignInScreen.route) {
                        popUpTo(navHostController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}