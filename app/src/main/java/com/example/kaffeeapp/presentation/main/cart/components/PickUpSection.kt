package com.example.kaffeeapp.presentation.main.cart.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun PickUpSection(
    branch: String,
    addressErrorValue: String,
    onClick: () -> Unit
) {
    Column {
        RoundedButtonWithIcon(
            backgroundColor = MaterialTheme.colorScheme.tertiary,
            borderStroke = BorderStroke(
                1.dp,
                if (addressErrorValue.isNotBlank()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
            ),
            leadingIconId = R.drawable.ic_edit,
            leadingIconDesc = "edit",
            trailingIconId = R.drawable.ic_arrow_right,
            trailingIconDesc = "arrow right",
            color = MaterialTheme.colorScheme.primary,
            text = if (branch == "") stringResource(id = R.string.select_nearby_branch) else branch
        ) {
            onClick.invoke()
        }
        if (addressErrorValue.isNotBlank()) {
            CustomizedText(
                text = addressErrorValue,
                fontSize = dimensionResource(id = R.dimen.text_size_small),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }

}