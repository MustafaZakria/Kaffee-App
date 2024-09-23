package com.example.kaffeeapp.presentation.main.favourite

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.EmptyList
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.components.TopBarTitle
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkType
import com.example.kaffeeapp.presentation.main.favourite.components.FavDrinkCard
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.util.model.Resource


@Composable
fun FavouriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel(),
    navigateToDetailsScreen: (String) -> Unit
) {
    val favDrinks = viewModel.favDrinks.collectAsState(initial = Resource.Loading())
    FavouriteScreenContent(
        drinks = favDrinks.value,
        onRemoveDrink = { id ->
            viewModel.removeFavDrink(id)
        },
        onAddToCartClick = { id ->
            navigateToDetailsScreen(id)
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavouriteScreenContent(
    drinks: Resource<List<Drink>>,
    onRemoveDrink: (String) -> Unit,
    onAddToCartClick: (String) -> Unit
) {
    Scaffold(containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopBarTitle(title = stringResource(id = R.string.favourites))
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = dimensionResource(id = R.dimen.padding_bottom_navigation),
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val drinksList = if (drinks is Resource.Success) {
                    drinks.data ?: emptyList()
                } else listOf()
                if(drinksList.isNotEmpty()) {
                    items(drinksList) { drink ->
                        FavDrinkCard(
                            drink = drink,
                            onRemoveClick = { id ->
                                onRemoveDrink.invoke(id)
                            },
                            onAddToCartClick = { id ->
                                onAddToCartClick(id)
                            }
                        )
                    }
                }
            }
            if (drinks is Resource.Loading) {
                ProgressBar(
                    modifier = Modifier.fillMaxSize()
                )
            } else if (drinks is Resource.Success && drinks.data?.isEmpty() == true) {
                EmptyList(stringResource(id = R.string.empty_list))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FavPreview() {

    val drinks = listOf(
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
    KaffeeAppTheme {
        FavouriteScreenContent(Resource.Success(drinks), {}) {}
    }
}