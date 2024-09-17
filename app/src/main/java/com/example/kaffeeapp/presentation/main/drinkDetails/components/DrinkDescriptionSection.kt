package com.example.kaffeeapp.presentation.main.drinkDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun DrinkDescriptionSection(drinkDescription: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        CustomizedText(
            text = stringResource(id = R.string.description),
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.tertiary
        )
        CustomizedText(
            text = drinkDescription,
            fontSize = dimensionResource(id = R.dimen.text_size_medium),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
}