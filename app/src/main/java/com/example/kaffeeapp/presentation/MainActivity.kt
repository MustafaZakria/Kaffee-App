package com.example.kaffeeapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kaffeeapp.navigation.NavGraph
import com.example.kaffeeapp.navigation.Screen
import com.example.kaffeeapp.presentation.sign_in.SignInScreen
import com.example.kaffeeapp.presentation.sign_in.SignInViewModel
import com.example.kaffeeapp.presentation.splash.SplashScreen
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            KaffeeAppTheme {
                KaffeeApp(navController = navController)
            }
        }
    }

    @Composable
    fun KaffeeApp(navController: NavHostController) {
        NavGraph(
            navController = navController
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KaffeeAppTheme {
//        SignInScreen()
    }
}