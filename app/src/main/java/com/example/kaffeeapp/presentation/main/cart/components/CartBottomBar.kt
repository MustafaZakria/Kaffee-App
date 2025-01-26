package com.example.kaffeeapp.presentation.main.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
fun CartBottomBar(
    orderTotalCost: String,
    onOrderClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
                vertical = dimensionResource(id = R.dimen.padding_medium)
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
            ) {
                CustomizedText(
                    text = stringResource(id = R.string.cash),
                    fontSize = dimensionResource(id = R.dimen.text_size_medium),
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontWeight = FontWeight.Medium
                )
                CustomizedText(
                    text = stringResource(id = R.string.drink_price, orderTotalCost),
                    fontSize = dimensionResource(id = R.dimen.text_size_18),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)))
            Button(
                onClick = { onOrderClick.invoke() },
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
                modifier = Modifier.weight(1f)
            ) {
                CustomizedText(
                    text = stringResource(id = R.string.order),
                    fontSize = dimensionResource(id = R.dimen.text_size_18),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}