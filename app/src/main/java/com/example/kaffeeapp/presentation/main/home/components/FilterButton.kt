package com.example.kaffeeapp.presentation.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.kaffeeapp.R

@Composable
fun FilterButton(
    modifier: Modifier,
    onClickFilterButton: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        IconButton(
            onClick = { onClickFilterButton.invoke() },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filter_icon),
                contentDescription = stringResource(id = R.string.filter_img_desc),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }
    }
}