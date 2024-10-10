package com.example.kaffeeapp.presentation.main.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.kaffeeapp.R

@Composable
fun SearchBar(
    modifier: Modifier,
    hint: String,
    searchStateValue: String,
    onSearchValueChange: (String) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        TextField(
            value = searchStateValue,
            onValueChange = { value ->
                onSearchValueChange.invoke(value)
            },
            singleLine = true,
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
            placeholder = {
                Text(
                    text = hint,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_img_desc),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}