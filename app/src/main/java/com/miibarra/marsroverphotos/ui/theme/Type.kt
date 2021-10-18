package com.miibarra.marsroverphotos.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import marsroverphotos.R

val SourceSanPro = FontFamily(
    Font(R.font.source_sans_pro_light, FontWeight.Light),
    Font(R.font.source_sans_pro_regular, FontWeight.Normal),
    Font(R.font.source_sans_pro_bold, FontWeight.Bold),
    Font(R.font.source_sans_pro_black, FontWeight.Black),
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = SourceSanPro,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)