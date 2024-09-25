package com.example.kaffeeapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun RoundedButton(
    backgroundColor: Color,
    borderStroke: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    text: String,
    textColor: Color,
    textModifier: Modifier,
    fontSize: Dp,
    roundedShapeSize: Dp,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable { onClick.invoke() },
        shape = RoundedCornerShape(roundedShapeSize),
        colors = CardDefaults.cardColors().copy(
            containerColor = backgroundColor
        ),
        border = borderStroke
    ) {
        CustomizedText(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            color = textColor,
            modifier = textModifier
        )
    }
}