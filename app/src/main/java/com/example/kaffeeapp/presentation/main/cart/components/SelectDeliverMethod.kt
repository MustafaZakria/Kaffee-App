package com.example.kaffeeapp.presentation.main.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun SelectDeliverMethod(
    modifier: Modifier,
    isDeliveryEnabled: Boolean,
    onDeliveryClick: () -> Unit,
    onPickUpClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_12)),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_x_small)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_small)))
                    .background(if (isDeliveryEnabled) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .weight(1f)
                    .clickable { onDeliveryClick.invoke() }
            ) {
                CustomizedText(
                    text = stringResource(id = R.string.delivery),
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    fontWeight = FontWeight.Medium,
                    color = if (isDeliveryEnabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.padding_small))
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_small)))
                    .background(if (!isDeliveryEnabled) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .weight(1f)
                    .clickable { onPickUpClick.invoke() }
            ) {
                CustomizedText(
                    text = stringResource(id = R.string.pick_up),
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.padding_small)),
                    color = if (!isDeliveryEnabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}