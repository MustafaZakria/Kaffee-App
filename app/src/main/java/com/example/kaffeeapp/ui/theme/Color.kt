package com.example.kaffeeapp.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val AccentColor = Color(0xFFC67C4E)
val LightBrown = Color(0xFFF9F2ED)
val LightGrey = Color(0xFFA2A2A2)
val NormalGrey = Color(0xFF313131)

val LightWhite = Color(0xFFF9F9F9)
val NormalWhite = Color(0xFFF4F4F4)
val NormalWhiteActive = Color(0xFFD8D8D8)
val DarkWhite = Color(0xFFEEEEEE)

val BannerColorStart = Color(0xFF111111)
val BannerColorCenter = Color(0xFF212121)
val BannerColorEnd = Color(0xFF313131)

val SearchBackgroundColor = Color(0xFF2A2A2A)
val LightRed = Color(0xFFED5151)

val ErrorColor = Color(0xFFB00020)

val Gold = Color(0xFFFBBE21)

val ColorScheme.gold: Color
    @Composable
    get() = Gold

val ColorScheme.lightBrown: Color
    @Composable
    get() = LightBrown

val ColorScheme.lightRed: Color
    @Composable
    get() = LightRed

val ColorScheme.searchBackgroundColor: Color
    @Composable
    get() = SearchBackgroundColor

val ColorScheme.bannerColorStart: Color
    @Composable
    get() = BannerColorStart

val ColorScheme.bannerColorCenter: Color
    @Composable
    get() = BannerColorCenter

val ColorScheme.bannerColorEnd: Color
    @Composable
    get() = BannerColorEnd

val ColorScheme.lightGrey: Color
    @Composable
    get() = LightGrey

val ColorScheme.normalGrey: Color
    @Composable
    get() = NormalGrey

val ColorScheme.normalWhiteActive: Color
    @Composable
    get() = NormalWhiteActive


val ColorScheme.accentColor: Color
    @Composable
    get() = AccentColor