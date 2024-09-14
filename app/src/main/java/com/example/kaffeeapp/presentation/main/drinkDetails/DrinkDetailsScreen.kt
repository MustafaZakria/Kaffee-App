package com.example.kaffeeapp.presentation.main.drinkDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.ImageLoaderWithUrl
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkType
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.ui.theme.lightGrey

@Composable
fun DrinkDetailsScreen(
    detailsViewModel: DrinkDetailsViewModel = hiltViewModel(),
    id: String
) {
    LaunchedEffect(key1 = Unit) {
        detailsViewModel.getDrinkById(id)
    }

    val drink = detailsViewModel.drink

    DrinkDetailsContent(drink)
}

@Composable
fun DrinkDetailsContent(drink: Drink) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
        ) {
            //spacer
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium)))
            //top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_icon),
                        contentDescription = stringResource(id = R.string.back_img_desc),
                        tint = Color.Black,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
                    )
                }
                CustomizedText(
                    text = stringResource(id = R.string.detail),
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.heart_outlined),
                        contentDescription = stringResource(id = R.string.heart_img_desc),
                        tint = Color.Black,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
                    )
                }
            }
            //spacer
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium)))
            //image
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ImageLoaderWithUrl(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large)))
                        .height(200.dp),
                    imageUrl = drink.imageUrl
                )
            }
            //spacer
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium)))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small))
                ) {
                    CustomizedText(
                        text = drink.name,
                        fontSize = dimensionResource(id = R.dimen.text_size_large),
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                    CustomizedText(
                        text = drink.type.name,
                        fontSize = dimensionResource(id = R.dimen.text_size_small),
                        color = MaterialTheme.colorScheme.lightGrey
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small))
                ) {
                    IconBoxed(icon = R.drawable.bike_icon)
                    IconBoxed(icon = R.drawable.bean_icon)
                    IconBoxed(icon = R.drawable.milk_icon)
                }
            }
        }
    }
}

@Composable
fun IconBoxed(
    icon: Int
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_small)))
            .background(MaterialTheme.colorScheme.onSurface)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_x_small))
                .size(dimensionResource(id = R.dimen.icon_size)),
            tint = MaterialTheme.colorScheme.primary
        )
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
        DrinkDetailsContent(drink)
    }
}