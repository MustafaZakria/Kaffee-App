package com.example.kaffeeapp.presentation.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.ui.theme.normalGrey
import com.example.kaffeeapp.util.Constants.ERROR
import com.example.kaffeeapp.util.Constants.KEY_MEDIUM_SIZE

@Composable
fun DrinkCard(
    drink: Drink,
    onClickDrink: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large)),
        onClick = { onClickDrink(drink.id) },
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_10))
        ) {
            var imageLoadingState by rememberSaveable { mutableStateOf(true) }
            var imageErrorState by rememberSaveable { mutableStateOf(false) }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(drink.imageUrl)
                        .listener(
                            onStart = { imageLoadingState = true },
                            onSuccess = { _, _ -> imageLoadingState = false },
                            onError = { _, _ ->
                                imageLoadingState = false
                                imageErrorState = true
                            }
                        )
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large)))
                )
                if (imageLoadingState) {
                    ProgressBar()
                }
                if (imageErrorState) {
                    Icon(
                        painter = painterResource(id = R.drawable.error_icon),
                        contentDescription = stringResource(id = R.string.error_img_desc),
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
                    )
                }
            }

            CustomizedText(
                text = drink.name,
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.text_size_18),
                color = MaterialTheme.colorScheme.normalGrey,
                style = MaterialTheme.typography.displaySmall,
                textLines = 1
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomizedText(
                    text = stringResource(
                        id = R.string.drink_price,
                        drink.price[KEY_MEDIUM_SIZE].toString()
                    ) ?: ERROR,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    color = MaterialTheme.colorScheme.normalGrey,
                    style = MaterialTheme.typography.displaySmall
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_small)))
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_img_desc),
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_x_small)),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}