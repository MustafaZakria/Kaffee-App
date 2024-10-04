package com.example.kaffeeapp.presentation.main.orders.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.entities.Order
import com.example.kaffeeapp.util.Constants.ADDRESS_KEY
import com.example.kaffeeapp.util.Constants.NAME_KEY

@Composable
fun MyOrderCard(
    order: Order
) {
    val location = if (order.isHomeDeliveryOrder)
        order.deliveryDetails[ADDRESS_KEY]
    else order.deliveryDetails[NAME_KEY]
    val date = order.getFormattedDate()
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DateAndLocationSection(
                date = date,
                location = location
            )
            order.drinkOrders.forEach { drinkOrder ->
                SingleDrinkOrderCard(order = drinkOrder)
            }
            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.padding_x_small))
            )
            TotalPriceSection(
                totalPrice = order.totalPrice
            )
        }
    }
}