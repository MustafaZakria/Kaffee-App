package com.example.kaffeeapp.presentation.main.home.components

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

@Composable
fun EmptyList() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.empty_icon),
            contentDescription = stringResource(id = R.string.error_img_desc),
            tint = Color.Black
        )
        CustomizedText(
            text = stringResource(id = R.string.empty_list),
            fontSize = dimensionResource(id = R.dimen.text_size_medium),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
}