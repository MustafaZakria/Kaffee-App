package com.example.kaffeeapp.presentation.main.drinkDetails.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkType
import com.example.kaffeeapp.presentation.main.drinkDetails.DrinkDetailsContent
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme

@Composable
fun DrinkDescriptionSection(drinkDescription: String) {
    Row(
//        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        var isExpanded by rememberSaveable { mutableStateOf(false) }
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            modifier = if(isExpanded) Modifier.fillMaxWidth() else Modifier.fillMaxWidth(0.83f)
                .animateContentSize()
        ) {
            CustomizedText(
                text = stringResource(id = R.string.description),
                fontSize = dimensionResource(id = R.dimen.text_size_16),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            CustomizedText(
                text = drinkDescription,
                fontSize = dimensionResource(id = R.dimen.text_size_medium),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.fillMaxWidth(),
                textLines = if (isExpanded) Int.MAX_VALUE else 2

            )
        }
        if (!isExpanded) {
            CustomizedText(
                text = "See More",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { isExpanded = true }
                    .padding(top = 4.dp)
                    .align(Alignment.Bottom),
                fontSize = dimensionResource(id = R.dimen.text_size_medium)
            )
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
        DrinkDescriptionSection("Our Hot Americano puts the oh! in Americano by combining two shots of 100% Rainforest Alliance Certified™ espresso with hot water creating a rich, robust drink.",
        )
    }
}