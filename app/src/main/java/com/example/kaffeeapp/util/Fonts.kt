package com.example.kaffeeapp.util

import android.os.Build
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R

object Fonts {
    val roboto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        FontFamily(
            Font(R.font.roboto_bold, weight = FontWeight.Bold),
            Font(R.font.roboto_medium, weight = FontWeight.Medium),
            Font(R.font.roboto_regular, weight = FontWeight.Normal)
        )
    } else {
        FontFamily.SansSerif
    }

    val sora = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        FontFamily(
            Font(R.font.sora_bold, weight = FontWeight.Bold),
            Font(R.font.sora_medium, weight = FontWeight.Medium),
            Font(R.font.roboto_regular, weight = FontWeight.Normal)
        )
    } else {
        FontFamily.SansSerif
    }
}