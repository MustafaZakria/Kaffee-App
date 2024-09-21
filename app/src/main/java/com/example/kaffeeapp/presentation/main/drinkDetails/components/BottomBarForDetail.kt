package com.example.kaffeeapp.presentation.main.drinkDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun BottomBarForDetail(
    priceValue: String, onClickBuyNow: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = dimensionResource(id = R.dimen.shape_rounded_corner_large),
                    topEnd = dimensionResource(id = R.dimen.shape_rounded_corner_large)
                )
            )
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
                vertical = dimensionResource(id = R.dimen.padding_large)
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
                    text = stringResource(id = R.string.price),
                    fontSize = dimensionResource(id = R.dimen.text_size_medium),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Normal
                )
                CustomizedText(
                    text = stringResource(id = R.string.drink_price, priceValue),
                    fontSize = dimensionResource(id = R.dimen.text_size_18),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)))
            Button(
                onClick = { onClickBuyNow.invoke() },
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cart_outlined),
                        contentDescription = "shopping cart",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
                    )
                    CustomizedText(
                        text = stringResource(id = R.string.add_to_cart),
                        fontSize = dimensionResource(id = R.dimen.text_size_18),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
                    )
                }

            }
        }
    }
}