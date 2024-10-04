package com.example.kaffeeapp.presentation.main.profile.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.kaffeeapp.R

@Composable
fun SettingItems(
    onMyOrderClick: () -> Unit
) {
    ProfileRowItem(
        text = stringResource(id = R.string.my_orders),
        onClick = { onMyOrderClick.invoke() }
    )
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_10)))
    ProfileRowItem(
        text = stringResource(id = R.string.about_us),
        onClick = {}
    )
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_10)))
    ProfileRowItem(
        text = stringResource(id = R.string.privacy_policy),
        onClick = {}
    )
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_10)))
    ProfileRowItem(
        text = stringResource(id = R.string.contact_us),
        onClick = {}
    )
}