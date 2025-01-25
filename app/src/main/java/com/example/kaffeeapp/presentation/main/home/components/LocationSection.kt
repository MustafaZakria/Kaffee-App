package com.example.kaffeeapp.presentation.main.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R

@Composable
fun LocationSection(
    modifier: Modifier,
    location: String,
    logout: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            CustomizedText(
                text = stringResource(id = R.string.location),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = dimensionResource(id = R.dimen.text_size_small),
                fontWeight = FontWeight.Normal
            )
            CustomizedText(
                text = location,
                fontSize = dimensionResource(id = R.dimen.text_size_medium),
                fontWeight = FontWeight.Medium
            )
        }
        IconButton(onClick = { logout.invoke() }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_logout),
                contentDescription = stringResource(id = R.string.logout),
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size)),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}