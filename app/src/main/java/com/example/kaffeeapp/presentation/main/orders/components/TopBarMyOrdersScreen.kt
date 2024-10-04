package com.example.kaffeeapp.presentation.main.orders.components

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun TopBarMyOrdersScreen(onBackButtonClick: () -> Unit) {
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
            onBackButtonClick.invoke()
        })
        {
            Icon(
                painter = painterResource(id = R.drawable.back_icon),
                contentDescription = stringResource(id = R.string.back_img_desc),
                tint = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }
        CustomizedText(
            text = stringResource(id = R.string.my_orders),
            fontSize = dimensionResource(id = R.dimen.text_size_18),
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
        )
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.back_icon),
                contentDescription = stringResource(id = R.string.heart_img_desc),
                tint = Color.Transparent,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }
    }
}