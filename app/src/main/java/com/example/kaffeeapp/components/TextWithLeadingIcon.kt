package com.example.kaffeeapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun TextWithLeadingIcon(
    text: String,
    icon: Int,
    textSize: Dp,
    fontWeight: FontWeight
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
        )
        CustomizedText(
            text = text,
            fontWeight = fontWeight,
            fontSize = textSize,
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.headlineSmall,
            textLines = 1,
        )
    }
}