package com.example.kaffeeapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kaffeeapp.presentation.main.cart.CartScreen
import com.example.kaffeeapp.presentation.main.favourite.FavouriteScreen
import com.example.kaffeeapp.presentation.main.home.HomeScreen
import com.example.kaffeeapp.presentation.main.notification.NotificationScreen
import com.example.kaffeeapp.util.Constants.CART_SCREEN
import com.example.kaffeeapp.util.Constants.FAVOURITE_SCREEN
import com.example.kaffeeapp.util.Constants.HOME_SCREEN
import com.example.kaffeeapp.util.Constants.NOTIFICATION_SCREEN


@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.HomeScreen.route,
        route = Graph.MainGraph.route,
//        modifier = modifier
    ) {
        composable(
            MainScreen.HomeScreen.route
        ) {
            HomeScreen()
        }
        composable(
            MainScreen.FavouriteScreen.route
        ) {
            FavouriteScreen()
        }
        composable(
            MainScreen.CartScreen.route
        ) {
            CartScreen()
        }
        composable(
            MainScreen.NotificationScreen.route
        ) {
            NotificationScreen()
        }
    }
}

sealed class MainScreen(val route: String) {
    data object HomeScreen : MainScreen(HOME_SCREEN)
    data object FavouriteScreen : MainScreen(FAVOURITE_SCREEN)
    data object CartScreen : MainScreen(CART_SCREEN)
    data object NotificationScreen : MainScreen(NOTIFICATION_SCREEN)
}