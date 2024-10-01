package com.example.kaffeeapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
fun EmptyList(
    message: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.empty_icon),
            contentDescription = stringResource(id = R.string.error_img_desc),
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size_large))
        )
        CustomizedText(
            text = message,
            fontSize = dimensionResource(id = R.dimen.text_size_medium),
            color = MaterialTheme.colorScheme.onTertiary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
}