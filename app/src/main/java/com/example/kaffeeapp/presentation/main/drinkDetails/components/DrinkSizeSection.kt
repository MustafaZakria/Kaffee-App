package com.example.kaffeeapp.presentation.main.drinkDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.data.entities.DrinkSize


@Composable
fun DrinkSizeSection(
    onSizeClicked: (DrinkSize) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        CustomizedText(
            text = stringResource(id = R.string.size),
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.tertiary
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var selectedSize by rememberSaveable { mutableStateOf(DrinkSize.SMALL) }
            SizeCard(
                modifier = Modifier.weight(1f),
                size = DrinkSize.SMALL,
                isClicked = selectedSize == DrinkSize.SMALL
            ) { drinkSize ->
                onSizeClicked.invoke(drinkSize)
                selectedSize = DrinkSize.SMALL
            }
            SizeCard(
                modifier = Modifier.weight(1f),
                size = DrinkSize.MEDIUM,
                isClicked = selectedSize == DrinkSize.MEDIUM
            ) { drinkSize ->
                onSizeClicked.invoke(drinkSize)
                selectedSize = DrinkSize.MEDIUM
            }
            SizeCard(
                modifier = Modifier.weight(1f),
                size = DrinkSize.LARGE,
                isClicked = selectedSize == DrinkSize.LARGE
            ) { drinkSize ->
                onSizeClicked.invoke(drinkSize)
                selectedSize = DrinkSize.LARGE
            }
        }
    }
}