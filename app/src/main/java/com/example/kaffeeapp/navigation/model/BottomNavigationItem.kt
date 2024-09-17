package com.example.kaffeeapp.navigation.model

import com.example.kaffeeapp.R
import com.example.kaffeeapp.navigation.MainScreen
import com.example.kaffeeapp.util.Constants.CART_TITLE
import com.example.kaffeeapp.util.Constants.FAVOURITE_TITLE
import com.example.kaffeeapp.util.Constants.HOME_TITLE
import com.example.kaffeeapp.util.Constants.NOTIFICATIONS_TITLE

data class BottomNavigationItem(
    val title: String = "",
    val route: String = "",
    val selectedIcon: Int = 0,
    val unselectedItem: Int = 0,
    val hasUpdate: Boolean = false
)

val bottomNavItems = listOf(
    BottomNavigationItem(
        title = HOME_TITLE,
        route = MainScreen.HomeScreen.route,
        selectedIcon = R.drawable.home_filled,
        unselectedItem = R.drawable.home_outlined,
        hasUpdate = false
    ),
    BottomNavigationItem(
        title = FAVOURITE_TITLE,
        route = MainScreen.FavouriteScreen.route,
        selectedIcon = R.drawable.heart_filled,
        unselectedItem = R.drawable.heart_outlined,
        hasUpdate = false
    ),
    BottomNavigationItem(
        title = CART_TITLE,
        route = MainScreen.CartScreen.route,
        selectedIcon = R.drawable.cart_filled,
        unselectedItem = R.drawable.cart_outlined,
        hasUpdate = false
    ),
    BottomNavigationItem(
        title = NOTIFICATIONS_TITLE,
        route = MainScreen.NotificationScreen.route,
        selectedIcon = R.drawable.notification_filled,
        unselectedItem = R.drawable.notification_outlined,
        hasUpdate = false
    )
)
val bottomNavRoutes = bottomNavItems.map { it.route }