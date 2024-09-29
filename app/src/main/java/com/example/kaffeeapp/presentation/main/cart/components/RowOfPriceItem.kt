package com.example.kaffeeapp.presentation.main.cart.components

import androidx.compose.foundation.layout.Arrangement
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
fun RowOfPriceItem(
    item: String,
    fontWeightItem: FontWeight = FontWeight.Normal,
    price: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomizedText(
            text = item,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = fontWeightItem
        )
        CustomizedText(
            text = price,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onTertiary,
            fontWeight = FontWeight.Medium
        )
    }
}