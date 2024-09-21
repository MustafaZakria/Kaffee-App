package com.example.kaffeeapp.presentation.main.home.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.kaffeeapp.util.Fonts.sora

@Composable
fun CustomizedText(
    text: String,
    style: TextStyle = TextStyle.Default,
    fontSize: Dp,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration? = null,
    modifier: Modifier = Modifier,
    textLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign? = null
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        fontSize = with(LocalContext.current) { fontSize.value.sp },
        color = color,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        fontFamily = sora,
        overflow = TextOverflow.Ellipsis,
        maxLines = textLines,
        textAlign = textAlign
    )
}