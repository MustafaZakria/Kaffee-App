package com.example.kaffeeapp.presentation.main.orders.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun TotalPriceSection(totalPrice: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomizedText(
            text = stringResource(id = R.string.total),
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.headlineSmall,
            textLines = 1,
        )
        CustomizedText(
            text = stringResource(id = R.string.cash),
            fontWeight = FontWeight.Normal,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
            textLines = 1,
        )
        CustomizedText(
            text = stringResource(id = R.string.drink_price, totalPrice),
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.headlineSmall,
            textLines = 1,
        )
    }
}