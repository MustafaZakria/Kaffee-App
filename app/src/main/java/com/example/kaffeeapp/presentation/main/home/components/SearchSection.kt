package com.example.kaffeeapp.presentation.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.kaffeeapp.R

@Composable
fun SearchSection(
    modifier: Modifier,
    searchStateValue: String,
    onSearchValueChange: (String) -> Unit,
    onClickFilterButton: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)))
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceContainerLow),
            hint = stringResource(id = R.string.search_hint),
            searchStateValue = searchStateValue
        ) { value ->
            onSearchValueChange.invoke(value)
        }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        FilterButton(
            Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)))
        ) {
            onClickFilterButton.invoke()
        }
    }
}