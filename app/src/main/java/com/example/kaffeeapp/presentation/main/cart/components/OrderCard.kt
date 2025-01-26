package com.example.kaffeeapp.presentation.main.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.ImageLoaderWithUrl
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun OrderCard(
    order: DrinkOrder,
    orderPrice: String,
    onDeleteClick: () -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        ) {
            //image
            Box(
                modifier = Modifier.align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center
            ) {
                ImageLoaderWithUrl(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size_85))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_6))),
                    imageUrl = order.imageUrl
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.Top)
                    .weight(1f)
                    .height(dimensionResource(id = R.dimen.image_size_85)),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CustomizedText(
                    text = order.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    color = MaterialTheme.colorScheme.onTertiary,
                    style = MaterialTheme.typography.headlineSmall,
                    textLines = 1,
                )
                CustomizedText(
                    text = order.size,
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    color = MaterialTheme.colorScheme.onBackground
                )
                CustomizedText(
                    text = stringResource(id = R.string.drink_price, orderPrice),
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    color = MaterialTheme.colorScheme.onTertiary,
                    style = MaterialTheme.typography.headlineSmall,
                    textLines = 1,
                )
            }

            OrderQuantityColumn(
                modifier = Modifier
                    .height(85.dp)
                    .weight(1f),
                quantity = order.quantity,
                onDeleteClick = { onDeleteClick.invoke() },
                onQuantityChange = { newQuantity -> onQuantityChange.invoke(newQuantity) }
            )
        }
    }
}