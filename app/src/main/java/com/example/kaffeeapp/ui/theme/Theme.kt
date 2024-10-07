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
    primary = Color(0xFFd39e76),
    onPrimary = Color.White,

    secondary = Color(0x5E323C48),
    onSecondary = Color.White,

    tertiary = Color(0xFF323C48),
    onTertiary = Color.White,

    surface = Color(0xFF222831),
    onSurface = Color.White,

    outline = Color(0x4F4E5E75),

    surfaceVariant = Color(0x7741576F),

    onBackground = Color(0x8DFDFDFD),

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
    /* Other default colors to override

    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun KaffeeAppTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    darkTheme: Boolean = true,
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