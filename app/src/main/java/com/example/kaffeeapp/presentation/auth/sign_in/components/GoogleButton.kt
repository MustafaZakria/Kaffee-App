package com.example.kaffeeapp.presentation.auth.sign_in.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.ui.theme.LightGrey
import com.example.kaffeeapp.util.Fonts.roboto

@Composable
fun GoogleButton(
    isDarkTheme: Boolean = false,
    onclickSignIn: () -> Unit
) {
    Surface(
        color = when (isDarkTheme) {
            true -> Color(0xFF131314)
            false -> Color(0xFFFFFFFF)
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)))
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = when (isDarkTheme) {
                        true -> Color(0xFF8E918F)
                        false -> Color.Transparent
                    }
                ),
                shape = CircleShape
            )
            .clickable { onclickSignIn.invoke() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_x_small))
        ) {
            Image(
                painterResource(id = R.drawable.google_logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_x_small))
                    .size(30.dp)
            )
            Text(
                text = "Continue with Google",
                fontSize = with(LocalContext.current) { dimensionResource(id = R.dimen.text_size_medium).value.sp },
                fontFamily = roboto,
                fontWeight = FontWeight.Medium,
                color = LightGrey,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_x_small))
            )
        }
    }
}