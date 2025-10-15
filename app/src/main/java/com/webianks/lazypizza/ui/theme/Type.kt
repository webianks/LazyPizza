package com.webianks.lazypizza.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.webianks.lazypizza.R

val InstrumentSansFontFamily = FontFamily(
    Font(R.font.instrument_sans_regular, FontWeight.Normal),
    Font(R.font.instrument_sans_medium, FontWeight.Medium),
    Font(R.font.instrument_sans_semibold, FontWeight.SemiBold),
    Font(R.font.instrument_sans_bold, FontWeight.Bold)
)

val Title1SemiBold = TextStyle(
    fontFamily = InstrumentSansFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
    lineHeight = 28.sp
)

val Title2 = TextStyle(
    fontFamily = InstrumentSansFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp,
    lineHeight = 24.sp
)

val Title3 = TextStyle(
    fontFamily = InstrumentSansFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 15.sp,
    lineHeight = 22.sp
)

val Label2Semibold = TextStyle(
    fontFamily = InstrumentSansFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 12.sp,
    lineHeight = 16.sp
)

val Body1Regular = TextStyle(
    fontFamily = InstrumentSansFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 22.sp
)

val Body1Medium = TextStyle(
    fontFamily = InstrumentSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 22.sp
)

val Body3Regular = TextStyle(
    fontFamily = InstrumentSansFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 18.sp
)

val Body3Medium = TextStyle(
    fontFamily = InstrumentSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 18.sp
)

val Body3Bold = TextStyle(
    fontFamily = InstrumentSansFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    lineHeight = 18.sp
)

val Body4Regular = TextStyle(
    fontFamily = InstrumentSansFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 16.sp
)

@Stable
data class AppTypography(
    val m3: Typography,
    // Variants that don’t fit in M3 slots:
    val bodyLargeMedium: TextStyle,
    val bodyMediumMedium: TextStyle,
    val bodyMediumBold: TextStyle
)

private val BaseM3Typography = Typography(
    headlineSmall = Title1SemiBold,
    titleLarge = Title2,
    titleMedium = Title3,
    bodyLarge = Body1Regular,
    bodyMedium = Body3Regular,
    bodySmall = Body4Regular,
    labelSmall = Label2Semibold
)

val AppTypographyDefaults = AppTypography(
    m3 = BaseM3Typography,
    bodyLargeMedium = Body1Medium,      // 16sp, Medium
    bodyMediumMedium = Body3Medium,     // 14sp, Medium
    bodyMediumBold = Body3Bold          // 14sp, Bold
)

val LocalAppTypography = staticCompositionLocalOf { AppTypographyDefaults }

object AppTextStyles {
    val Title1SemiBold: TextStyle
        @Composable get() = LocalAppTypography.current.m3.headlineSmall
    val Title2: TextStyle
        @Composable get() = LocalAppTypography.current.m3.titleLarge
    val Title3: TextStyle
        @Composable get() = LocalAppTypography.current.m3.titleMedium

    val Body1Regular: TextStyle
        @Composable get() = LocalAppTypography.current.m3.bodyLarge
    val Body3Regular: TextStyle
        @Composable get() = LocalAppTypography.current.m3.bodyMedium
    val Body4Regular: TextStyle
        @Composable get() = LocalAppTypography.current.m3.bodySmall

    val Label2Semibold: TextStyle
        @Composable get() = LocalAppTypography.current.m3.labelSmall

    val Body1Medium: TextStyle
        @Composable get() = LocalAppTypography.current.bodyLargeMedium
    val Body3Medium: TextStyle
        @Composable get() = LocalAppTypography.current.bodyMediumMedium
    val Body3Bold: TextStyle
        @Composable get() = LocalAppTypography.current.bodyMediumBold
}
