package com.example.kaffeeapp.presentation.main.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.entities.User


@Composable
fun UserStatisticalSection(
    points: String,
    userInfo: User
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        StatisticItem(
            modifier = Modifier.weight(1f),
            value = points,
            text = stringResource(id = R.string.points)
        )
        VerticalDivider(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.height_divider_50))
                .align(Alignment.CenterVertically)
        )
        StatisticItem(
            modifier = Modifier.weight(1f),
            value = userInfo.orders.size.toString(),
            text = stringResource(id = R.string.orders)
        )
    }
}