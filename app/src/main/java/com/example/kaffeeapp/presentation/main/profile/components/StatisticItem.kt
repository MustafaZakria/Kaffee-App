package com.example.kaffeeapp.presentation.main.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun StatisticItem(
    modifier: Modifier,
    value: String,
    text: String
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomizedText(
            text = value,
            fontSize = dimensionResource(id = R.dimen.text_size_large),
            color = MaterialTheme.colorScheme.onTertiary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_x_small)))
        CustomizedText(
            text = text,
            fontSize = dimensionResource(id = R.dimen.text_size_medium),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}