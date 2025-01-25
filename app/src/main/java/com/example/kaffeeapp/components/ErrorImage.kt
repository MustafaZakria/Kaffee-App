package com.example.kaffeeapp.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.example.kaffeeapp.R

@Composable
fun ErrorImage() {
    Icon(
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_error),
        contentDescription = stringResource(id = R.string.error_img_desc),
        tint = MaterialTheme.colorScheme.error,
        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size_large))
    )
}