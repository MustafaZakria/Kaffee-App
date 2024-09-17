package com.example.kaffeeapp.presentation.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkType
import com.example.kaffeeapp.presentation.main.home.components.BackgroundBanner
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.presentation.main.home.components.DrinksSection
import com.example.kaffeeapp.presentation.main.home.components.DrinksSelectTypeSection
import com.example.kaffeeapp.presentation.main.home.components.FilterButton
import com.example.kaffeeapp.presentation.main.home.components.OfferBannerSection
import com.example.kaffeeapp.presentation.main.home.components.SearchBar
import com.example.kaffeeapp.repository.SelectedType
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.ui.theme.lightGrey
import com.example.kaffeeapp.ui.theme.searchBackgroundColor
import com.example.kaffeeapp.util.model.Resource

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    logout: () -> Unit,
    navigateToDetailScreen: (String) -> Unit
) {
    val drinks by homeViewModel.drinks.observeAsState(emptyList())
    val selectedType by homeViewModel.drinkSelectedType.observeAsState(SelectedType.ALL_DRINKS)
    val drinksResponse = homeViewModel.drinkApiResponse
    val searchState by homeViewModel.searchValueState.observeAsState("")

    MainScreenContent(
        drinks = drinks,
        drinksResponse = drinksResponse,
        onSearchValueChange = { value -> homeViewModel.onSearchValueChange(value = value) },
        searchValue = searchState,
        logout = {
            logout.invoke()
            homeViewModel.signOut()
        },
        selectedType = selectedType,
        onDrinkTypeSelect = { type -> homeViewModel.setSelectedType(type = type) },
        navigateToDetailScreen = { id -> navigateToDetailScreen.invoke(id) }
    )
}

@Composable
fun MainScreenContent(
    drinks: List<Drink>,
    drinksResponse: Resource<Boolean>,
    onSearchValueChange: (String) -> Unit,
    searchValue: String,
    logout: () -> Unit,
    selectedType: SelectedType,
    onDrinkTypeSelect: (SelectedType) -> Unit,
    navigateToDetailScreen: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
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
                logout = { logout.invoke() }
            )
            //spacer
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)))
            //search and filter section
            SearchSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeightIn(max = 50.dp),
                searchStateValue = searchValue,
                onSearchValueChange = { value -> onSearchValueChange.invoke(value) },
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
                drinkSelectedType = selectedType,
            ) { type ->
                onDrinkTypeSelect.invoke(type)
            }
            //spacer
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_x_small)))
            //drinks section
            DrinksSection(
                drinks = drinks,
                drinksResponse = drinksResponse
            ) { id ->
                navigateToDetailScreen.invoke(id)
            }
        }

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
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = dimensionResource(id = R.dimen.text_size_small),
                fontWeight = FontWeight.Normal
            )
            CustomizedText(
                text = location,
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
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)))
                .weight(1f)
                .background(MaterialTheme.colorScheme.searchBackgroundColor),
            hint = stringResource(id = R.string.search_hint),
            searchStateValue = searchStateValue
        ) { value ->
            onSearchValueChange.invoke(value)
        }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        FilterButton(
            Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)))
        ) {
            onClickFilterButton.invoke()
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
        ),
        Drink(
            id = "2",
            name = "Coffee",
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/kaffee-app-8b7ab.appspot.com/o/cold_brew.jpg?alt=media&token=df326fdc-ae67-48b0-859a-28c65ad641eb",
            description = "Our Hot Americano puts the oh! in Americano by combining two shots of 100% Rainforest Alliance Certified™ espresso with hot water creating a rich, robust drink.",
            ingredients = listOf("water", "coffee"),
            price = mapOf(Pair("small", "20"), Pair("medium", "30"), Pair("large", "40")),
            type = DrinkType.COLD
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
        MainScreenContent(
            drinks,
            Resource.Loading(),
            {},
            "",
            {},
            SelectedType.ALL_DRINKS,
            {}
        ) {}
    }

}