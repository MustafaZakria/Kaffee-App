package com.example.kaffeeapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = LightAccentColor,
    onPrimary = Color.White,

    secondary = DarkGray850,
    onSecondary = Color.White,

    tertiary = DarkGray900,
    onTertiary = Color.White,

    surface = DarkGray950,
    onSurface = Color.White,

    outline = DarkGray600,

    surfaceVariant = DarkGray700,

    onBackground = White50,

    error = ErrorColor,

    surfaceContainer = BannerDarkColorStart,
    surfaceContainerHigh = BannerDarkColorCenter,
    surfaceContainerHighest = BannerDarkColorEnd,

    surfaceContainerLow = SearchBackgroundDarkColor
)

private val LightColorScheme = lightColorScheme(
    primary = AccentColor,
    onPrimary = Color.White,

    secondary = NormalWhiteActive,
    onSecondary = NormalGrey,

    tertiary = Color.White,
    onTertiary = NormalGrey,

    surface = NormalWhite,
    onSurface = Color.Black,

    onBackground = LightGrey,
    error = ErrorColor,
    outline = LightBrown,
    surfaceVariant = LightWhite,

    surfaceContainer = BannerColorStart,
    surfaceContainerHigh = BannerColorCenter,
    surfaceContainerHighest = BannerColorEnd,

    surfaceContainerLow = SearchBackgroundColor
)

@Composable
fun KaffeeAppTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}