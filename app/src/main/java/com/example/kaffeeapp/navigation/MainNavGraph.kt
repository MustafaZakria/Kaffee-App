package com.example.kaffeeapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kaffeeapp.navigation.model.Graph
import com.example.kaffeeapp.presentation.main.cart.CartScreen
import com.example.kaffeeapp.presentation.main.cart.CartViewModel
import com.example.kaffeeapp.presentation.main.drinkDetails.DrinkDetailsScreen
import com.example.kaffeeapp.presentation.main.favourite.FavouriteScreen
import com.example.kaffeeapp.presentation.main.home.HomeScreen
import com.example.kaffeeapp.presentation.main.map.MapScreen
import com.example.kaffeeapp.presentation.main.orders.MyOrdersScreen
import com.example.kaffeeapp.presentation.main.profile.ProfileScreen
import com.example.kaffeeapp.util.Constants.CART_SCREEN
import com.example.kaffeeapp.util.Constants.DRINK_DETAIL_SCREEN
import com.example.kaffeeapp.util.Constants.FAVOURITE_SCREEN
import com.example.kaffeeapp.util.Constants.HOME_SCREEN
import com.example.kaffeeapp.util.Constants.ID_KEY
import com.example.kaffeeapp.util.Constants.MAP_SCREEN
import com.example.kaffeeapp.util.Constants.MY_ORDERS_SCREEN
import com.example.kaffeeapp.util.Constants.PROFILE_SCREEN


@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier,
    logout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.HomeScreen.route,
        route = Graph.MainGraph.route,
    ) {
        composable(
            route = MainScreen.HomeScreen.route
        ) {
            HomeScreen(
                logout = {
                    logout.invoke()
                }
            ) { id ->
                navController.navigate("${MainScreen.DrinkDetailScreen.route}/$id")
            }
        }
        composable(
            route = "${MainScreen.DrinkDetailScreen.route}/{$ID_KEY}",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { navBackStackEntry ->
            val parentEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(Graph.MainGraph.route)
            }
            val viewModel: CartViewModel = hiltViewModel(parentEntry)
            DrinkDetailsScreen(
                cartViewModel = viewModel
            ) {
                navController.popBackStack()
            }
        }
        composable(
            route = MainScreen.FavouriteScreen.route
        ) {
            FavouriteScreen { id ->
                navController.navigate("${MainScreen.DrinkDetailScreen.route}/$id")
            }
        }
        composable(
            route = MainScreen.CartScreen.route
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Graph.MainGraph.route)
            }
            val viewModel: CartViewModel = hiltViewModel(parentEntry)

            CartScreen(viewModel = viewModel) {
                navController.navigate(MainScreen.MapScreen.route)
            }
        }
        composable(
            route = MainScreen.ProfileScreen.route
        ) {
            ProfileScreen {
                navController.navigate(MainScreen.MyOrdersScreen.route)
            }
        }
        composable(
            route = MainScreen.MapScreen.route
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Graph.MainGraph.route)
            }
            val viewModel: CartViewModel = hiltViewModel(parentEntry)
            MapScreen(orderDetailsViewModel = viewModel) {
                navController.popBackStack()
            }
        }
        composable(
            route = MainScreen.MyOrdersScreen.route
        ) {
            MyOrdersScreen {
                navController.popBackStack()
            }
        }
    }
}


sealed class MainScreen(val route: String) {
    data object HomeScreen : MainScreen(HOME_SCREEN)
    data object DrinkDetailScreen : MainScreen(DRINK_DETAIL_SCREEN)
    data object FavouriteScreen : MainScreen(FAVOURITE_SCREEN)
    data object CartScreen : MainScreen(CART_SCREEN)
    data object ProfileScreen : MainScreen(PROFILE_SCREEN)
    data object MapScreen : MainScreen(MAP_SCREEN)
    data object MyOrdersScreen : MainScreen(MY_ORDERS_SCREEN)
}