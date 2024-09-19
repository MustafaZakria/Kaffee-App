package com.example.kaffeeapp.presentation.main.drinkDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.example.kaffeeapp.R

@Composable
fun IconBoxed(
    icon: Int
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_small)))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_x_small))
                .size(dimensionResource(id = R.dimen.icon_size)),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}