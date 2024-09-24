package com.example.kaffeeapp.presentation.main.drinkDetails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkSize
import com.example.kaffeeapp.data.entities.DrinkType
import com.example.kaffeeapp.presentation.main.cart.CartViewModel
import com.example.kaffeeapp.presentation.main.drinkDetails.components.BottomBarForDetail
import com.example.kaffeeapp.presentation.main.drinkDetails.components.DrinkDescriptionSection
import com.example.kaffeeapp.presentation.main.drinkDetails.components.DrinkImageSection
import com.example.kaffeeapp.presentation.main.drinkDetails.components.DrinkInfoSection
import com.example.kaffeeapp.presentation.main.drinkDetails.components.DrinkSizeSection
import com.example.kaffeeapp.presentation.main.drinkDetails.components.TopBarForDetail
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme

@Composable
fun DrinkDetailsScreen(
    cartViewModel: CartViewModel,
    drinkDetailsViewModel: DrinkDetailsViewModel = hiltViewModel(),
    id: String,
    onBackClick: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        drinkDetailsViewModel.getDrinkById(id)
    }
    val drink = drinkDetailsViewModel.drink

    var drinkPrice by rememberSaveable { mutableStateOf("") }

    var isFavDrink by rememberSaveable { mutableStateOf(false) }

    var drinkSize by rememberSaveable { mutableStateOf(DrinkSize.SMALL) }

    LaunchedEffect(key1 = drink.value.price) {
        if (drink.value.price.isNotEmpty()) {
            drinkPrice = drinkDetailsViewModel.getDrinkPrice(DrinkSize.SMALL)
        }
        isFavDrink = drinkDetailsViewModel.isDrinkFav()
    }


    DrinkDetailsContent(
        drink = drink.value,
        onBackClick = {
            onBackClick.invoke()
        },
        onSizeClicked = { size ->
            drinkPrice = drinkDetailsViewModel.getDrinkPrice(size)
            drinkSize = size
        },
        drinkPrice = drinkPrice,
        onFavouriteClick = {
            isFavDrink = !isFavDrink
            if (isFavDrink) {
                drinkDetailsViewModel.addDrinkToFav()
            } else {
                drinkDetailsViewModel.removeDrinkFromFav()
            }
        },
        onBuyClick = {
            cartViewModel.addDrinkToCart(drink.value, drinkSize)
            onBackClick.invoke()
        },
        isFav = isFavDrink
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DrinkDetailsContent(
    drink: Drink,
    onBackClick: () -> Unit,
    onSizeClicked: (DrinkSize) -> Unit,
    drinkPrice: String,
    isFav: Boolean,
    onFavouriteClick: () -> Unit,
    onBuyClick: () -> Unit
) {
    Scaffold(containerColor = MaterialTheme.colorScheme.surface, bottomBar = {
        BottomBarForDetail(
            priceValue = drinkPrice
        ) {
            onBuyClick.invoke()
        }
    }, topBar = {
        TopBarForDetail(onBackClick = { onBackClick.invoke() }, onFavouriteClick = {
            onFavouriteClick.invoke()
        }, isFav = isFav
        )
    }) { innerPadding ->

        val scrollState = rememberScrollState()

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                    .verticalScroll(scrollState)

            ) {
                //image
                DrinkImageSection(imageUrl = drink.imageUrl)
                //spacer
                Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)))
                //drink info
                DrinkInfoSection(drink = drink)
                //spacer
                Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)))
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                )
                //spacer
                Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)))
                //Size
                DrinkSizeSection(onSizeClicked = { drinkSize ->
                    onSizeClicked.invoke(drinkSize)
                })
                //spacer
                Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium)))
                //description
                DrinkDescriptionSection(drinkDescription = drink.description)

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrinkDetailsPreview() {
    KaffeeAppTheme {
        val drink = Drink(
            id = "1",
            name = "Americano",
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/kaffee-app-8b7ab.appspot.com/o/americano.jpg?alt=media&token=b6ce3d37-bffe-4a37-8cbc-7c1cbb7241f7",
            description = "Our Hot Americano puts the oh! in Americano by combining two shots of 100% Rainforest Alliance Certified™ espresso with hot water creating a rich, robust drink.",
            ingredients = listOf("water", "coffee"),
            price = mapOf(Pair("small", "20"), Pair("medium", "30"), Pair("large", "40")),
            type = DrinkType.HOT,
            rating = "3.4"
        )
        DrinkDetailsContent(drink, {}, {}, "", false, {}, {})
    }
}