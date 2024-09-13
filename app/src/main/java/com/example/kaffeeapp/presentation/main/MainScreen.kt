package com.example.kaffeeapp.presentation.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kaffeeapp.R
import com.example.kaffeeapp.navigation.MainNavGraph
import com.example.kaffeeapp.navigation.bottomNavItems
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.ui.theme.accentColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    logout: () -> Unit
) {
    val navHostController = rememberNavController()
    val clearRippleConfiguration = RippleConfiguration(
        color = Color.Transparent, rippleAlpha = RippleAlpha(
            draggedAlpha = 0.0f,
            focusedAlpha = 0.0f,
            hoveredAlpha = 0.0f,
            pressedAlpha = 0.0f,
        )
    )

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            bottomBar = {
                CompositionLocalProvider(
                    LocalRippleConfiguration provides clearRippleConfiguration
                ) {
                    NavigationBar(
                        containerColor = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .height(80.dp)
                    ) {
                        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        Spacer(modifier = Modifier.weight(0.2f))
                        bottomNavItems.forEach { item ->
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                                onClick = {
                                    navHostController.navigate(item.route) {
                                        //avoid large stack
                                        popUpTo(navHostController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        //avoid multiple copies on re-selecting same item
                                        launchSingleTop = true
                                        //restore state
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    BadgedBox(
                                        badge = {
                                            if (item.hasUpdate)
                                                Badge()
                                        }
                                    ) {
                                        Icon(
                                            painter = if (currentDestination?.hierarchy?.any { it.route == item.route } == true)
                                                painterResource(id = item.selectedIcon)
                                            else painterResource(id = item.unselectedItem),
                                            contentDescription = item.title,
                                            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
                                        )
                                    }
                                },
                                colors = NavigationBarItemColors(
                                    selectedIconColor = MaterialTheme.colorScheme.accentColor,
                                    unselectedIconColor = Color.Gray,
                                    selectedIndicatorColor = Color.Transparent,
                                    unselectedTextColor = Color.Transparent,
                                    disabledIconColor = Color.Transparent,
                                    disabledTextColor = Color.Transparent,
                                    selectedTextColor = Color.Transparent
                                ),
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.2f))
                    }
                }
            }
        ) { innerPadding ->
            MainNavGraph(
                navController = navHostController,
                modifier = Modifier.padding(innerPadding),
                logout = logout
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    KaffeeAppTheme {
        MainScreen {}
    }
}