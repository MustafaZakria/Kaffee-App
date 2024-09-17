package com.example.kaffeeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kaffeeapp.navigation.model.Graph
import com.example.kaffeeapp.presentation.main.cart.CartScreen
import com.example.kaffeeapp.presentation.main.drinkDetails.DrinkDetailsScreen
import com.example.kaffeeapp.presentation.main.drinkDetails.DrinkDetailsViewModel
import com.example.kaffeeapp.presentation.main.favourite.FavouriteScreen
import com.example.kaffeeapp.presentation.main.home.HomeScreen
import com.example.kaffeeapp.presentation.main.notification.NotificationScreen
import com.example.kaffeeapp.util.Constants.CART_SCREEN
import com.example.kaffeeapp.util.Constants.DRINK_DETAIL_SCREEN
import com.example.kaffeeapp.util.Constants.DRINK_ID_KEY
import com.example.kaffeeapp.util.Constants.FAVOURITE_SCREEN
import com.example.kaffeeapp.util.Constants.HOME_SCREEN
import com.example.kaffeeapp.util.Constants.NOTIFICATION_SCREEN
import com.example.kaffeeapp.util.Utils.sharedViewModel


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
//        modifier = modifier
    ) {
        composable(
            MainScreen.HomeScreen.route
        ) {
            HomeScreen(logout = { logout.invoke() }) { id ->
                navController.navigate("${MainScreen.DrinkDetailScreen.route}/$id")
            }
        }
        composable(
            route = "${MainScreen.DrinkDetailScreen.route}/{$DRINK_ID_KEY}"
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString(DRINK_ID_KEY)
            id?.let { drinkId ->
                DrinkDetailsScreen(id = drinkId) {
                    navController.popBackStack()
                }
            }
        }
        composable(
            MainScreen.FavouriteScreen.route
        ) { backStackEntry ->
//            val detailsViewModel =
//                backStackEntry.sharedViewModel<DrinkDetailsViewModel>(navController = navController)
            FavouriteScreen()
        }
        composable(
            MainScreen.CartScreen.route
        ) {
            CartScreen()
        }
        composable(
            MainScreen.NotificationScreen.route
        ) { backStackEntry ->

            NotificationScreen()
        }
    }
}


sealed class MainScreen(val route: String) {
    data object HomeScreen : MainScreen(HOME_SCREEN)
    data object DrinkDetailScreen : MainScreen(DRINK_DETAIL_SCREEN)
    data object FavouriteScreen : MainScreen(FAVOURITE_SCREEN)
    data object CartScreen : MainScreen(CART_SCREEN)
    data object NotificationScreen : MainScreen(NOTIFICATION_SCREEN)
}