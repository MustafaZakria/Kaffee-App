package com.example.kaffeeapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kaffeeapp.navigation.RootNavGraph
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            KaffeeAppTheme {
                KaffeeApp(navHostController = navController)
            }
        }
    }
}

@Composable
fun KaffeeApp(navHostController: NavHostController) {
    RootNavGraph(
        navHostController = navHostController
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KaffeeAppTheme {
//        SignInScreen()
    }
}