package com.example.kaffeeapp.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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

val BannerDarkColorStart = Color(0xFF222831)
val BannerDarkColorCenter = Color(0xFF222831)
val BannerDarkColorEnd = Color(0xFF222831)

val SearchBackgroundColor = Color(0xFF2A2A2A)
val SearchBackgroundDarkColor = Color(0x97303844)

val LightRed = Color(0xFFED5151)

val ErrorColor = Color(0xFFB00020)

val Gold = Color(0xFFFF7A31)

val ColorScheme.gold: Color
    @Composable
    get() = Gold

val ColorScheme.lightWhite: Color
    @Composable
    get() = LightWhite

val ColorScheme.lightBrown: Color
    @Composable
    get() = LightBrown

val ColorScheme.lightRed: Color
    @Composable
    get() = LightRed

val ColorScheme.searchBackgroundColor: Color
    @Composable
    get() = SearchBackgroundColor


