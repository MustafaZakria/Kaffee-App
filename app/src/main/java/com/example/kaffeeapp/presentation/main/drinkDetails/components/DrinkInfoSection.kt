package com.example.kaffeeapp.presentation.main.drinkDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun DrinkInfoSection(
    drinkName: String, drinkType: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            CustomizedText(
                text = drinkName,
                fontSize = dimensionResource(id = R.dimen.text_size_large),
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Medium
            )
            CustomizedText(
                text = drinkType,
                fontSize = dimensionResource(id = R.dimen.text_size_medium),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            IconBoxed(icon = R.drawable.bike_icon)
            IconBoxed(icon = R.drawable.bean_icon)
            IconBoxed(icon = R.drawable.milk_icon)
        }
    }
}