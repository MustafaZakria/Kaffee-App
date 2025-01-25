package com.example.kaffeeapp.presentation.main.orders.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.TextWithLeadingIcon

@Composable
fun DateAndLocationSection(date: String, location: String?) {
    TextWithLeadingIcon(
        text = date,
        iconId = R.drawable.ic_calendar,
        textSize = dimensionResource(id = R.dimen.text_size_16),
        fontWeight = FontWeight.Medium
    )
    TextWithLeadingIcon(
        text = location.toString(),
        iconId = R.drawable.ic_location,
        textSize = dimensionResource(id = R.dimen.text_size_16),
        fontWeight = FontWeight.Medium
    )
}