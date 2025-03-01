package com.example.kaffeeapp.presentation.main.cart.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun RoundedButtonWithIcon(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    borderStroke: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    leadingIconId: Int,
    leadingIconDesc: String,
    trailingIconId: Int? = null,
    trailingIconDesc: String = "",
    color: Color,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium))) // Clip to match shape
            .background(backgroundColor)
            .border(
                border = borderStroke,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium))
            )
            .clickable(onClick = { onClick.invoke() })
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_10),
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(leadingIconId),
                contentDescription = leadingIconDesc,
                tint = color,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
            CustomizedText(
                text = text,
                fontSize = dimensionResource(id = R.dimen.text_size_medium),
                fontWeight = FontWeight.Medium,
                color = color,
                modifier = Modifier
            )
            if (trailingIconId != null) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = ImageVector.vectorResource(trailingIconId),
                    contentDescription = trailingIconDesc,
                    tint = color,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
                )
            }
        }
    }
}