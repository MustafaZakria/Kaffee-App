package com.example.kaffeeapp.presentation.main.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.repository.SelectedType
import com.example.kaffeeapp.ui.theme.accentColor
import com.example.kaffeeapp.ui.theme.normalGrey
import com.example.kaffeeapp.ui.theme.normalWhiteActive

@Composable
fun SelectTypeCard(
    type: SelectedType,
    drinkSelectedType: SelectedType,
    onClickType: (SelectedType) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(
                horizontal =
                if (type != SelectedType.ALL_DRINKS)
                    dimensionResource(id = R.dimen.padding_small)
                else (0.dp)
            ),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_small)),
        onClick = {
            onClickType(type)
        },
        colors =
        CardDefaults.cardColors().copy(
            containerColor = if (drinkSelectedType == type)
                MaterialTheme.colorScheme.accentColor
            else MaterialTheme.colorScheme.normalWhiteActive
        )
    ) {
        CustomizedText(
            text = type.value,
            fontSize = dimensionResource(id = R.dimen.text_size_medium),
            color = if (drinkSelectedType == type)
                MaterialTheme.colorScheme.normalWhiteActive
            else MaterialTheme.colorScheme.normalGrey,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(
                vertical = dimensionResource(id = R.dimen.padding_x_small),
                horizontal = dimensionResource(id = R.dimen.padding_medium)
            )
        )
    }
}