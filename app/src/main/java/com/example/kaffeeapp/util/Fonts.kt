package com.example.kaffeeapp.util

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R

object Fonts {
    val roboto = FontFamily(
        Font(R.font.roboto_bold, weight = FontWeight.Bold),
        Font(R.font.roboto_medium, weight = FontWeight.Medium),
        Font(R.font.roboto_regular, weight = FontWeight.Normal)
    )

    val sora = FontFamily(
        Font(R.font.sora_bold, weight = FontWeight.Bold),
        Font(R.font.sora_medium, weight = FontWeight.Medium),
        Font(R.font.roboto_regular, weight = FontWeight.Normal)
    )
}