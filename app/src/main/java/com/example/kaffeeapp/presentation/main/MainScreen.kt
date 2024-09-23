package com.example.kaffeeapp.presentation.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kaffeeapp.R
import com.example.kaffeeapp.navigation.MainNavGraph
import com.example.kaffeeapp.navigation.model.bottomNavItems
import com.example.kaffeeapp.navigation.model.bottomNavRoutes
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.util.Utils.clearRippleConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    logout: () -> Unit
) {
    val navHostController = rememberNavController()

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            bottomBar = {
                if (currentDestination?.route in bottomNavRoutes) {
                    CompositionLocalProvider(
                        LocalRippleConfiguration provides clearRippleConfiguration
                    ) {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier
                                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large)))
                                .heightIn(max = dimensionResource(id = R.dimen.main_bottom_navigation_height))
                        ) {
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
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
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
            }
        ) { innerPadding ->
            MainNavGraph(
                navController = navHostController,
                modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                logout = {
                    logout.invoke()
                }
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