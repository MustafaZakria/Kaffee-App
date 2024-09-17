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
import com.example.kaffeeapp.data.entities.DrinkType
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.util.model.DrinkSize
import com.example.kaffeeapp.presentation.main.drinkDetails.components.BottomBarForDetail
import com.example.kaffeeapp.presentation.main.drinkDetails.components.DrinkDescriptionSection
import com.example.kaffeeapp.presentation.main.drinkDetails.components.DrinkImageSection
import com.example.kaffeeapp.presentation.main.drinkDetails.components.DrinkInfoSection
import com.example.kaffeeapp.presentation.main.drinkDetails.components.DrinkSizeSection
import com.example.kaffeeapp.presentation.main.drinkDetails.components.TopBarForDetail

@Composable
fun DrinkDetailsScreen(
    detailsViewModel: DrinkDetailsViewModel = hiltViewModel(),
    id: String,
    onBackClick: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        detailsViewModel.getDrinkById(id)
    }
    val drink = detailsViewModel.drink

    var drinkPrice by rememberSaveable {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = drink.value.price) {
        if (drink.value.price.isNotEmpty()) {
            drinkPrice = detailsViewModel.getDrinkPrice(DrinkSize.SMALL)
        }
    }

    var isFavDrink by rememberSaveable { mutableStateOf(false) }

    DrinkDetailsContent(
        drink = drink.value,
        onBackClick = {
            onBackClick.invoke()
        },
        onSizeClicked = { drinkSize ->
            drinkPrice = detailsViewModel.getDrinkPrice(drinkSize)
        },
        drinkPrice = drinkPrice,
        onFavouriteClick = {
            isFavDrink = !isFavDrink
        },
        onBuyClick = {
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
                DrinkInfoSection(
                    drinkName = drink.name, drinkType = drink.type.name
                )
                //spacer
                Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)))
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                )
                //spacer
                Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)))
                //description
                DrinkDescriptionSection(drinkDescription = drink.description)
                //spacer
                Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium)))
                //Size
                DrinkSizeSection(onSizeClicked = { drinkSize ->
                    onSizeClicked.invoke(drinkSize)
                })
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
            type = DrinkType.HOT
        )
        DrinkDetailsContent(drink, {}, {}, "", false, {}, {})
    }
}