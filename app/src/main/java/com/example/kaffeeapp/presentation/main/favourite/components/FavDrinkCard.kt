package com.example.kaffeeapp.presentation.main.favourite.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
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
import com.example.kaffeeapp.components.RoundedButton
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun FavDrinkCard(
    drink: Drink,
    onAddToCartClick: (String) -> Unit,
    onRemoveClick: (String) -> Unit
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
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
            verticalAlignment = Alignment.Top
        ) {
            //image
            Box(
                contentAlignment = Alignment.Center
            ) {
                ImageLoaderWithUrl(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_x_small))),
                    imageUrl = drink.imageUrl
                )
            }
            //content
            Column(
                modifier = Modifier.height(100.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    CustomizedText(
                        text = drink.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(id = R.dimen.text_size_18),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.headlineSmall,
                        textLines = 1
                    )
                    CustomizedText(
                        text = drink.getFormattedIngredients(),
                        fontSize = dimensionResource(id = R.dimen.text_size_medium),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    RoundedButton(
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        text = stringResource(id = R.string.add_to_cart),
                        textColor = MaterialTheme.colorScheme.onPrimary,
                        textModifier = Modifier.padding(
                            vertical = dimensionResource(id = R.dimen.padding_x_small),
                            horizontal = dimensionResource(id = R.dimen.padding_small)
                        ),
                        fontSize = dimensionResource(id = R.dimen.text_size_small),
                        roundedShapeSize = dimensionResource(id = R.dimen.shape_rounded_corner_6)
                    ) {
                        onAddToCartClick.invoke(drink.id)
                    }
                    RoundedButton(
                        backgroundColor = MaterialTheme.colorScheme.tertiary,
                        borderStroke = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        text = stringResource(id = R.string.remove),
                        textColor = MaterialTheme.colorScheme.primary,
                        textModifier = Modifier.padding(
                            vertical = dimensionResource(id = R.dimen.padding_x_small),
                            horizontal = dimensionResource(id = R.dimen.padding_small)
                        ),
                        fontSize = dimensionResource(id = R.dimen.text_size_small),
                        roundedShapeSize = dimensionResource(id = R.dimen.shape_rounded_corner_6)
                    ) {
                        onRemoveClick.invoke(drink.id)
                    }
                }
            }
        }
    }
}