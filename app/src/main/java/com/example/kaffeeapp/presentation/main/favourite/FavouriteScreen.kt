package com.example.kaffeeapp.presentation.main.favourite

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.EmptyList
import com.example.kaffeeapp.components.ImageLoaderWithUrl
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkSize
import com.example.kaffeeapp.data.entities.DrinkType
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
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
            TopBarFavourite()
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = 64.dp,
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
            if (drinks is Resource.Loading) {
                ProgressBar(
                    modifier = Modifier.fillMaxSize()
                )
            } else if (drinks is Resource.Success && drinks.data?.isEmpty() == true) {
                EmptyList()
            }
        }
    }
}

@Composable
fun FavDrinkCard(
    drink: Drink,
    onAddToCartClick: (String) -> Unit,
    onRemoveClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
            verticalAlignment = Alignment.Top
        ) {
            //image
            Box(
                contentAlignment = Alignment.Center
            ) {
                ImageLoaderWithUrl(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_x_small))),
                    imageUrl = drink.imageUrl
                )
            }
            //content
            Column(
                modifier = Modifier.height(100.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    CustomizedText(
                        text = drink.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(id = R.dimen.text_size_18),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.headlineSmall,
                        textLines = 1
                    )
                    CustomizedText(
                        text = drink.getFormattedIngredients(),
                        fontSize = dimensionResource(id = R.dimen.text_size_medium),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    verticalAlignment = Alignment.Bottom,
                ) {
//                    var isSizesExpanded by rememberSaveable { mutableStateOf(false) }
//                    var drinkSize by rememberSaveable { mutableStateOf(DrinkSize.SMALL) }
//                    if (!isSizesExpanded) {
                    RoundedButton(
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        text = stringResource(id = R.string.add_to_cart),
                        textColor = MaterialTheme.colorScheme.onPrimary
                    ) {
//                            isSizesExpanded = true
                        onAddToCartClick.invoke(drink.id)
                    }
//                    } else {
//                        CircleDrinkSize(
//                            modifier = Modifier.align(Alignment.CenterVertically),
//                            drinkSize = DrinkSize.SMALL,
//                        ) {
//                            isSizesExpanded = false
//                            drinkSize = DrinkSize.SMALL
//                        }
//                        CircleDrinkSize(
//                            modifier = Modifier.align(Alignment.CenterVertically),
//                            drinkSize = DrinkSize.MEDIUM,
//                        ) {
//                            isSizesExpanded = false
//                            drinkSize = DrinkSize.MEDIUM
//                        }
//                        CircleDrinkSize(
//                            modifier = Modifier.align(Alignment.CenterVertically),
//                            drinkSize = DrinkSize.LARGE,
//                        ) {
//                            isSizesExpanded = false
//                            drinkSize = DrinkSize.LARGE
//                        }
//                    }

                    RoundedButton(
                        backgroundColor = Color.Transparent,
                        borderStroke = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
                        text = stringResource(id = R.string.remove),
                        textColor = MaterialTheme.colorScheme.onTertiary
                    ) {
                        onRemoveClick.invoke(drink.id)
                    }
                }
            }
        }
    }
}

@Composable
fun RoundedButton(
    backgroundColor: Color,
    borderStroke: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    text: String,
    textColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable { onClick.invoke() },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large)),
        colors = CardDefaults.cardColors().copy(
            containerColor = backgroundColor
        ),
        border = borderStroke
    ) {
        CustomizedText(
            text = text,
            fontSize = dimensionResource(id = R.dimen.text_size_small),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(
                vertical = dimensionResource(id = R.dimen.padding_x_small),
                horizontal = dimensionResource(id = R.dimen.padding_small)
            ),
            color = textColor
        )
    }
}


@Composable
fun CircleDrinkSize(
    modifier: Modifier,
    drinkSize: DrinkSize,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable {
                onClick.invoke()
            }
            .size(20.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            CustomizedText(
                text = drinkSize.shortened,
                fontSize = dimensionResource(id = R.dimen.text_size_small),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun TopBarFavourite() {
    Surface(
        modifier = Modifier
            .padding(
                top = dimensionResource(id = R.dimen.padding_x_large),
                start = dimensionResource(id = R.dimen.padding_medium),
                end = dimensionResource(id = R.dimen.padding_medium),
                bottom = dimensionResource(id = R.dimen.padding_large),
            )
            .background(Color.Transparent)
            .fillMaxWidth()
    ) {
        CustomizedText(
            text = stringResource(id = R.string.favourites),
            fontSize = dimensionResource(id = R.dimen.text_size_18),
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
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