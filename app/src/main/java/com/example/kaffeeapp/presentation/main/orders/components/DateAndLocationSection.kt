package com.example.kaffeeapp.presentation.main.orders.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.TextWithLeadingIcon
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun DateAndLocationSection(date: String, location: String?) {
    TextWithLeadingIcon(
        text = date,
        icon = R.drawable.calendar_icon,
        textSize = dimensionResource(id = R.dimen.text_size_16),
        fontWeight = FontWeight.Medium
    )
    TextWithLeadingIcon(
        text = location.toString(),
        icon = R.drawable.location_icon,
        textSize = dimensionResource(id = R.dimen.text_size_16),
        fontWeight = FontWeight.Medium
    )
}