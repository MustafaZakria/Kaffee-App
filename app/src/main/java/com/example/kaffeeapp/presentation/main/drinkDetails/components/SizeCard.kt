package com.example.kaffeeapp.presentation.main.drinkDetails.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.util.model.DrinkSize
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.ui.theme.lightBrown

@Composable
fun SizeCard(
    modifier: Modifier, size: DrinkSize, isClicked: Boolean, onSizeClick: (DrinkSize) -> Unit
) {
    Card(modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_12)),
        colors = CardDefaults.cardColors().copy(
            containerColor = if (isClicked) MaterialTheme.colorScheme.lightBrown else MaterialTheme.colorScheme.tertiary
        ),
        border = BorderStroke(
            width = 1.dp,
            if (isClicked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        ),
        onClick = {
            onSizeClick.invoke(size)
        }) {
        CustomizedText(
            text = size.shortened,
            color = if (isClicked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary,
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_10)
                ),
            textAlign = TextAlign.Center
        )
    }
}