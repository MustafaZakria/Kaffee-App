package com.example.kaffeeapp.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.kaffeeapp.R

@Composable
fun ErrorImage() {
    Icon(
        painter = painterResource(id = R.drawable.error_icon),
        contentDescription = stringResource(id = R.string.error_img_desc),
        tint = MaterialTheme.colorScheme.error,
        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
    )
}