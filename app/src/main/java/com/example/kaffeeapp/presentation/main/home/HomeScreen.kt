package com.example.kaffeeapp.presentation.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkType
import com.example.kaffeeapp.presentation.auth.sign_in.components.GradientBackground.gradientBackground
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.presentation.main.home.components.FilterButton
import com.example.kaffeeapp.presentation.main.home.components.SearchBar
import com.example.kaffeeapp.presentation.main.home.components.SelectTypeCard
import com.example.kaffeeapp.repository.SelectedType
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.ui.theme.accentColor
import com.example.kaffeeapp.ui.theme.bannerColorCenter
import com.example.kaffeeapp.ui.theme.bannerColorEnd
import com.example.kaffeeapp.ui.theme.bannerColorStart
import com.example.kaffeeapp.ui.theme.lightGrey
import com.example.kaffeeapp.ui.theme.lightRed
import com.example.kaffeeapp.ui.theme.normalGrey
import com.example.kaffeeapp.ui.theme.normalWhiteActive
import com.example.kaffeeapp.ui.theme.searchBackgroundColor
import com.example.kaffeeapp.util.Constants.ERROR
import com.example.kaffeeapp.util.Constants.KEY_MEDIUM_SIZE

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val drinks by homeViewModel.drinks.observeAsState(emptyList())

    MainScreenContent(drinks)

}

@Composable
fun MainScreenContent(drinks: List<Drink>) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val scrollState = rememberScrollState() //for drinks type row

        //background banner
        BackgroundBanner()
        //content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            //spacer
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium)))
            //location section
            LocationSection(
                modifier = Modifier
                    .fillMaxWidth(),
                location = "Cairo, Giza",
                logout = {}
            )
            //spacer
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)))
            //search and filter section
            SearchSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeightIn(max = 50.dp),
                searchStateValue = "",
                onSearchValueChange = {},
                onClickFilterButton = {}
            )
            //spacer
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)))
            //offer photo section
            OfferBannerSection(Modifier)
            //spacer
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_x_small)))
            //drinks type section
            DrinksSelectTypeSection(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .fillMaxWidth(),
                drinkSelectedType = SelectedType.ALL_DRINKS,
            ) {}
            //spacer
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_x_small)))
            //drinks section
            DrinksSection(
                drinks = drinks
            ) {}
        }

    }
}


@Composable
fun BackgroundBanner() {
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

@Composable
fun LocationSection(
    modifier: Modifier,
    location: String,
    logout: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            CustomizedText(
                text = stringResource(id = R.string.location),
                color = MaterialTheme.colorScheme.lightGrey,
                fontSize = dimensionResource(id = R.dimen.text_size_small),
                fontWeight = FontWeight.Normal
            )
            CustomizedText(
                text = location,
                color = MaterialTheme.colorScheme.normalWhiteActive,
                fontSize = dimensionResource(id = R.dimen.text_size_medium),
                fontWeight = FontWeight.Medium
            )
        }
        IconButton(onClick = { logout.invoke() }) {
            Image(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = stringResource(id = R.string.logout),
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }
    }
}

@Composable
fun SearchSection(
    modifier: Modifier,
    searchStateValue: String,
    onSearchValueChange: (String) -> Unit,
    onClickFilterButton: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large)))
                .weight(1f)
                .background(MaterialTheme.colorScheme.searchBackgroundColor),
            hint = stringResource(id = R.string.search_hint),
            searchStateValue = searchStateValue
        ) { value ->
            onSearchValueChange(value)
        }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        FilterButton(
            Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large)))
        ) {
            onClickFilterButton.invoke()
        }
    }
}

@Composable
fun OfferBannerSection(
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large))),
            painter = painterResource(id = R.drawable.img_offer_banner),
            contentDescription = stringResource(id = R.string.offer_img_desc),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_small)))
            ) {
                CustomizedText(
                    text = stringResource(id = R.string.promo),
                    fontSize = dimensionResource(id = R.dimen.text_size_medium),
                    color = MaterialTheme.colorScheme.normalWhiteActive,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.lightRed)
                        .padding(dimensionResource(id = R.dimen.padding_x_small)),
                )
            }
            CustomizedText(
                text = stringResource(id = R.string.buy_one_get_one),
                style = MaterialTheme.typography.headlineMedium,
                fontSize = dimensionResource(id = R.dimen.text_size_x_large),
                color = MaterialTheme.colorScheme.normalWhiteActive,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
            )
        }
    }
}

@Composable
fun DrinksSelectTypeSection(
    modifier: Modifier,
    drinkSelectedType: SelectedType,
    onClickType: (SelectedType) -> Unit
) {
    Row(
        modifier = modifier
    ) {
        SelectedType.entries.forEach { type ->
            SelectTypeCard(
                type = type,
                drinkSelectedType = drinkSelectedType,
                onClickType = { selectedType -> onClickType(selectedType) }
            )
        }
    }
}

@Composable
fun DrinksSection(
    drinks: List<Drink>,
    onClickDrink: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 64.dp),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        items(drinks) { drink ->
            DrinkCard(
                drink = drink,
                onClickDrink = { id -> onClickDrink(id) }
            )
        }
    }
}

@Composable
fun DrinkCard(
    drink: Drink,
    onClickDrink: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large)),
        onClick = { onClickDrink(drink.id) },
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_10))
        ) {
            AsyncImage(
                model = drink.imageUrl,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large)))
            )
            CustomizedText(
                text = drink.name,
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.text_size_18),
                color = MaterialTheme.colorScheme.normalGrey,
                style = MaterialTheme.typography.displaySmall,
                textLines = 1
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomizedText(
                    text = stringResource(
                        id = R.string.drink_price,
                        drink.price[KEY_MEDIUM_SIZE].toString()
                    ) ?: ERROR,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    color = MaterialTheme.colorScheme.normalGrey,
                    style = MaterialTheme.typography.displaySmall
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_small)))
                        .background(MaterialTheme.colorScheme.accentColor)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_x_small)),
                        tint = MaterialTheme.colorScheme.normalWhiteActive
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
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
        MainScreenContent(drinks)
    }

}