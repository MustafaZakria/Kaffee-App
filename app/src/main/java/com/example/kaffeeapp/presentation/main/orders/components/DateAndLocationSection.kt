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
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun DateAndLocationSection(date: String, location: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.calendar_icon),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
        )
        CustomizedText(
            text = date,
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.headlineSmall,
            textLines = 1,
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.location_icon),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
        )
        CustomizedText(
            text = location.toString(),
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.headlineSmall,
            textLines = 1,
        )
    }
}