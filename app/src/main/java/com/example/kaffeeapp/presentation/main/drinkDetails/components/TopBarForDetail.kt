package com.example.kaffeeapp.presentation.main.drinkDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun TopBarForDetail(
    onBackClick: () -> Unit,
    onFavouriteClick: () -> Unit,
    isFav: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.padding_x_large),
                start = dimensionResource(id = R.dimen.padding_medium),
                end = dimensionResource(id = R.dimen.padding_medium),
                bottom = dimensionResource(id = R.dimen.padding_medium),
            )
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onBackClick.invoke()
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_left),
                contentDescription = stringResource(id = R.string.back_img_desc),
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }
        CustomizedText(
            text = stringResource(id = R.string.detail),
            fontSize = dimensionResource(id = R.dimen.text_size_18),
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
        )
        IconButton(onClick = {
            onFavouriteClick.invoke()
        }) {
            Icon(
                imageVector = if (isFav) ImageVector.vectorResource(id = R.drawable.ic_heart_filled) else ImageVector.vectorResource(
                    id = R.drawable.ic_heart_outlined
                ),
                contentDescription = stringResource(id = R.string.heart_img_desc),
                tint = if (isFav) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }
    }
}