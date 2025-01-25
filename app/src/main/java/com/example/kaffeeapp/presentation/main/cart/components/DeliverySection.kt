package com.example.kaffeeapp.presentation.main.cart.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun DeliverySection(
    address: String,
    addressErrorValue: String,
    onEditAddressClick: () -> Unit,
    onAddNoteClick: () -> Unit
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundedButtonWithIcon(
                modifier = Modifier.weight(1f),
                backgroundColor = MaterialTheme.colorScheme.tertiary,
                leadingIconId = R.drawable.ic_edit,
                leadingIconDesc = "edit",
                trailingIconId = R.drawable.ic_arrow_right,
                trailingIconDesc = "arrow right",
                color = MaterialTheme.colorScheme.primary,
                text = if (address == "") stringResource(id = R.string.add_address) else address,
                borderStroke = BorderStroke(
                    1.dp,
                    if (addressErrorValue.isNotBlank()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                )
            ) {
                onEditAddressClick.invoke()
            }
            RoundedButtonWithIcon(
                backgroundColor = MaterialTheme.colorScheme.tertiary,
                leadingIconId = R.drawable.ic_document,
                leadingIconDesc = "add",
                color = MaterialTheme.colorScheme.primary,
                text = stringResource(id = R.string.add_note),
                borderStroke = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline
                )
            ) {
                onAddNoteClick.invoke()
            }
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