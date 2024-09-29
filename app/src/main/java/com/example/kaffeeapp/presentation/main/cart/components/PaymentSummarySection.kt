package com.example.kaffeeapp.presentation.main.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.util.model.OrderCost

@Composable
fun PaymentSummarySection(
    orderCost: OrderCost,
    isDeliveryEnabled: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small))
        ) {
            //items
            RowOfPriceItem(
                item = stringResource(id = R.string.items),
                price = stringResource(id = R.string.price_value, orderCost.itemsPrice)
            )
            //discount
            RowOfPriceItem(
                item = stringResource(id = R.string.discount),
                price = stringResource(id = R.string.discount_price, orderCost.discountValue)
            )
            if (isDeliveryEnabled) {
                //delivery
                RowOfPriceItem(
                    item = stringResource(id = R.string.delivery),
                    price = if (orderCost.deliveryFee.toDouble() == 0.0) stringResource(id = R.string.free) else stringResource(
                        id = R.string.price_value,
                        orderCost.deliveryFee
                    )
                )
            }
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_x_small)))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_x_small)))
            //total
            RowOfPriceItem(
                item = stringResource(id = R.string.total),
                price = stringResource(id = R.string.drink_price, orderCost.getTotalCost()),
                fontWeightItem = FontWeight.Medium
            )
        }
    }
}