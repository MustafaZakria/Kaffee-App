package com.example.kaffeeapp.presentation.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkType
import com.example.kaffeeapp.presentation.auth.sign_in.components.GradientBackground.gradientBackground
import com.example.kaffeeapp.ui.theme.accentColor
import com.example.kaffeeapp.ui.theme.bannerColorCenter
import com.example.kaffeeapp.ui.theme.bannerColorEnd
import com.example.kaffeeapp.ui.theme.bannerColorStart
import com.example.kaffeeapp.ui.theme.lightGrey
import com.example.kaffeeapp.ui.theme.lightRed
import com.example.kaffeeapp.ui.theme.normalWhiteActive
import com.example.kaffeeapp.ui.theme.searchBackgroundColor

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
            //location section
            LocationSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.padding_medium)),
                location = "Cairo, Giza",
                logout = {}
            )
            //search and filter section
            SearchSection(
                modifier = Modifier.fillMaxWidth(),
                searchStateValue = "",
                onSearchValueChange = {},
                onClickFilterButton = {}
            )
            //spacer
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)))
            //offer photo section
            OfferBannerSection(Modifier)
            //selected type section
            //drinks section
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
            Text(
                text = stringResource(id = R.string.location),
                color = MaterialTheme.colorScheme.lightGrey,
                fontSize = with(LocalContext.current) { dimensionResource(id = R.dimen.text_size_small).value.sp },
//                        fontFamily = sora,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_x_small)))
            Text(
                text = location,
                color = MaterialTheme.colorScheme.normalWhiteActive,
                fontSize = with(LocalContext.current) { dimensionResource(id = R.dimen.text_size_medium).value.sp },
//                        fontFamily = sora,
                fontWeight = FontWeight.Medium
            )
        }
        IconButton(onClick = { logout.invoke() }) {
            Image(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = stringResource(id = R.string.logout)
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
            Modifier.clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large)))
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
                Text(
                    text = stringResource(id = R.string.promo),
                    fontSize = with(LocalContext.current) { dimensionResource(id = R.dimen.text_size_medium).value.sp },
                    color = MaterialTheme.colorScheme.normalWhiteActive,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.lightRed)
                        .padding(dimensionResource(id = R.dimen.padding_x_small))
                )
            }

            Text(
                text = stringResource(id = R.string.buy_one_get_one),
                fontSize = with(LocalContext.current) { dimensionResource(id = R.dimen.text_size_x_large).value.sp },
                color = MaterialTheme.colorScheme.normalWhiteActive,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier,
    hint: String,
    searchStateValue: String,
    onSearchValueChange: (String) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        TextField(
            value = searchStateValue,
            onValueChange = { value ->
                onSearchValueChange.invoke(value)
            },
            singleLine = true,
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large)),
            placeholder = {
                Text(
                    text = hint,
                    color = MaterialTheme.colorScheme.lightGrey
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_img_desc),
                    tint = MaterialTheme.colorScheme.normalWhiteActive
                )
            },
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun FilterButton(
    modifier: Modifier,
    onClickFilterButton: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        IconButton(
            onClick = { onClickFilterButton.invoke() },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.accentColor)
                .padding(dimensionResource(id = R.dimen.padding_x_small))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filter_icon),
                contentDescription = stringResource(id = R.string.filter_img_desc),
                tint = MaterialTheme.colorScheme.normalWhiteActive
            )
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
    MainScreenContent(drinks)
}