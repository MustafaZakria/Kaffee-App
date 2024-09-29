package com.example.kaffeeapp.presentation.main.cart.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun OrderQuantityColumn(
    modifier: Modifier,
    quantity: Int,
    onDeleteClick: () -> Unit,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    onDeleteClick.invoke()
                },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.close_icon),
                contentDescription = "delete",
                tint = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.size(24.dp)
            )
        }
        //drink quantity
        Row(
            modifier = Modifier
                .align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(dimensionResource(id = R.dimen.circle_size_32))
                    .padding(dimensionResource(id = R.dimen.padding_x_small))
                    .clickable {
                        onMinusClick.invoke()
                    },
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                shape = CircleShape,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides
                            if (quantity == 1) MaterialTheme.colorScheme.onTertiary.copy(
                                alpha = 0.2f
                            )
                            else MaterialTheme.colorScheme.onTertiary.copy(alpha = 1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.negative_icon),
                        contentDescription = "minus",
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.padding_x_small))
                    )
                }
            }
            CustomizedText(
                text = quantity.toString(),
                fontSize = dimensionResource(id = R.dimen.text_size_medium),
                color = MaterialTheme.colorScheme.onTertiary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
            Card(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(dimensionResource(id = R.dimen.circle_size_32))
                    .padding(dimensionResource(id = R.dimen.padding_x_small))
                    .clickable {
                        onPlusClick.invoke()
                    },
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                shape = CircleShape,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.positive_icon),
                    contentDescription = "minus",
                    tint = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_x_small))
                )
            }
        }

    }
}