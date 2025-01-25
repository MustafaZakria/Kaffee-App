package com.example.kaffeeapp.navigation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.kaffeeapp.R
import com.example.kaffeeapp.navigation.MainScreen
import com.example.kaffeeapp.util.Constants.CART_TITLE
import com.example.kaffeeapp.util.Constants.FAVOURITE_TITLE
import com.example.kaffeeapp.util.Constants.HOME_TITLE
import com.example.kaffeeapp.util.Constants.PROFILE_TITLE

data class BottomNavigationItem(
    val title: String = "",
    val route: String = "",
    val selectedIcon: Int = 0,
    val unselectedItem: Int = 0,
    val hasUpdate: MutableState<Boolean> = mutableStateOf(false)
)

val bottomNavItems = listOf(
    BottomNavigationItem(
        title = HOME_TITLE,
        route = MainScreen.HomeScreen.route,
        selectedIcon = R.drawable.ic_home_filled,
        unselectedItem = R.drawable.ic_home_outlined
    ),
    BottomNavigationItem(
        title = FAVOURITE_TITLE,
        route = MainScreen.FavouriteScreen.route,
        selectedIcon = R.drawable.ic_heart_filled,
        unselectedItem = R.drawable.ic_heart_outlined,
    ),
    BottomNavigationItem(
        title = CART_TITLE,
        route = MainScreen.CartScreen.route,
        selectedIcon = R.drawable.ic_cart_filled,
        unselectedItem = R.drawable.ic_cart_outlined,
    ),
    BottomNavigationItem(
        title = PROFILE_TITLE,
        route = MainScreen.ProfileScreen.route,
        selectedIcon = R.drawable.ic_profile_filled,
        unselectedItem = R.drawable.ic_profile_outlined,
    )
)
val bottomNavRoutes = bottomNavItems.map { it.route }