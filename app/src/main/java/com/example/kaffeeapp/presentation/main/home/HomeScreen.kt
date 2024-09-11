package com.example.kaffeeapp.presentation.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkType
import com.example.kaffeeapp.presentation.auth.sign_in.components.GradientBackground.gradientBackground
import com.example.kaffeeapp.ui.theme.bannerColorCenter
import com.example.kaffeeapp.ui.theme.bannerColorEnd
import com.example.kaffeeapp.ui.theme.bannerColorStart

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val drinks by homeViewModel.drinks.observeAsState(emptyList())

    MainScreenContent(drinks)

}

@Composable
fun MainScreenContent(drinks: List<Drink>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        //location section
        //search and filter section
        //offer photo section
        //selected type section
        //drinks section
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .height(maxHeight * 0.3f)
                    .fillMaxWidth()
                    .gradientBackground(
                        listOf(
                            MaterialTheme.colorScheme.bannerColorStart,
                            MaterialTheme.colorScheme.bannerColorCenter,
                            MaterialTheme.colorScheme.bannerColorEnd
                        ), 240f
                    )
            ) {}
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    val drinks = listOf<Drink>(
        Drink(
            id = "1",
            name = "Americano",
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/kaffee-app-8b7ab.appspot.com/o/americano.jpg?alt=media&token=b6ce3d37-bffe-4a37-8cbc-7c1cbb7241f7",
            description = "Our Hot Americano puts the oh! in Americano by combining two shots of 100% Rainforest Alliance Certified™ espresso with hot water creating a rich, robust drink.",
            ingredients = listOf("water", "coffee"),
            price = mapOf(Pair("small", "20"), Pair("medium", "30"), Pair("large", "40")),
            type = DrinkType.HOT
        ),
        Drink(
            id = "2",
            name = "Coffee",
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/kaffee-app-8b7ab.appspot.com/o/cold_brew.jpg?alt=media&token=df326fdc-ae67-48b0-859a-28c65ad641eb",
            description = "Our Hot Americano puts the oh! in Americano by combining two shots of 100% Rainforest Alliance Certified™ espresso with hot water creating a rich, robust drink.",
            ingredients = listOf("water", "coffee"),
            price = mapOf(Pair("small", "20"), Pair("medium", "30"), Pair("large", "40")),
            type = DrinkType.COLD
        )
    )
    MainScreenContent(drinks)
}