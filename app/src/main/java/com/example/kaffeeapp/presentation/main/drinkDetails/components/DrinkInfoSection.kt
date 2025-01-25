package com.example.kaffeeapp.presentation.main.drinkDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.ui.theme.gold

@Composable
fun DrinkInfoSection(
    drink: Drink
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            CustomizedText(
                text = drink.name,
                fontSize = dimensionResource(id = R.dimen.text_size_large),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
            CustomizedText(
                text = drink.getFormattedIngredients(),
                fontSize = dimensionResource(id = R.dimen.text_size_16),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = Color(0x22FF7A31)
            ),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large))

        ) {
            Row(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(id = R.dimen.padding_x_small),
                        horizontal = dimensionResource(id = R.dimen.padding_small)
                    ),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_star),
                    contentDescription = "star",
                    tint = MaterialTheme.colorScheme.gold,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
                )
                CustomizedText(
                    text = drink.rating,
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    color = MaterialTheme.colorScheme.gold,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}