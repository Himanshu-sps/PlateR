package com.plater.android.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.plater.android.R

private val googleFontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val poppinsBold = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = googleFontProvider,
        weight = FontWeight.Bold
    )
)

private val poppinsRegular = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = googleFontProvider,
        weight = FontWeight.Normal
    )
)

private object PlateRTypography {
    val TitleBold = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = 50.sp,
        lineHeight = 75.sp
    )
    val TitleRegular = TextStyle(
        fontFamily = poppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 50.sp,
        lineHeight = 75.sp
    )
    val HeaderBold = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 45.sp
    )
    val HeaderRegular = TextStyle(
        fontFamily = poppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        lineHeight = 45.sp
    )
    val LargeBold = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 30.sp
    )
    val LargeRegular = TextStyle(
        fontFamily = poppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 30.sp
    )
    val MediumBold = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 27.sp
    )
    val MediumRegular = TextStyle(
        fontFamily = poppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 27.sp
    )
    val NormalBold = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp
    )
    val NormalRegular = TextStyle(
        fontFamily = poppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    )
    val SmallBold = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 21.sp
    )
    val SmallRegular = TextStyle(
        fontFamily = poppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 21.sp
    )
    val SmallerBold = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 17.sp
    )
    val SmallerRegular = TextStyle(
        fontFamily = poppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 17.sp
    )
}

val Typography = Typography(
    displayLarge = PlateRTypography.TitleBold,
    displayMedium = PlateRTypography.TitleRegular,
    headlineLarge = PlateRTypography.HeaderBold,
    headlineMedium = PlateRTypography.HeaderRegular,
    titleLarge = PlateRTypography.LargeBold,
    titleMedium = PlateRTypography.LargeRegular,
    titleSmall = PlateRTypography.MediumBold,
    headlineSmall = PlateRTypography.MediumRegular,
    bodyLarge = PlateRTypography.NormalBold,
    bodyMedium = PlateRTypography.NormalRegular,
    bodySmall = PlateRTypography.SmallBold,
    labelMedium = PlateRTypography.SmallRegular,
    labelLarge = PlateRTypography.SmallerBold,
    labelSmall = PlateRTypography.SmallerRegular
)