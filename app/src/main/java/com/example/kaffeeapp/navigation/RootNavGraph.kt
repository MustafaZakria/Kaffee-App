package com.example.kaffeeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kaffeeapp.navigation.model.Graph
import com.example.kaffeeapp.presentation.main.MainScreen

@Composable
fun RootNavGraph(
    navHostController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Graph.AuthGraph.route,
        route = Graph.RootGraph.route,
        modifier = Modifier
    ) {
        authNavGraph(navHostController = navHostController)
        composable(route = Graph.MainGraph.route) {
            MainScreen(
                logout = {
                    navHostController.navigate(AuthScreen.SignInScreen.route) {
                        popUpTo(navHostController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}