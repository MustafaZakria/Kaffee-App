package com.example.kaffeeapp.presentation.main.drinkDetails.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.ImageLoaderWithUrl

@Composable
fun DrinkImageSection(imageUrl: String) {
    Box(
        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        ImageLoaderWithUrl(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)))
                .height(300.dp), imageUrl = imageUrl
        )
    }
}