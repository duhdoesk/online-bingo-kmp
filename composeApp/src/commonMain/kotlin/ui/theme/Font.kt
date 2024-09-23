package ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import themedbingo.composeapp.generated.resources.Poppins_Black
import themedbingo.composeapp.generated.resources.Poppins_Bold
import themedbingo.composeapp.generated.resources.Poppins_ExtraBold
import themedbingo.composeapp.generated.resources.Poppins_ExtraLight
import themedbingo.composeapp.generated.resources.Poppins_Light
import themedbingo.composeapp.generated.resources.Poppins_Medium
import themedbingo.composeapp.generated.resources.Poppins_Regular
import themedbingo.composeapp.generated.resources.Poppins_SemiBold
import themedbingo.composeapp.generated.resources.Poppins_Thin
import themedbingo.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PoppinsFontFamily() = FontFamily(
    Font(Res.font.Poppins_Black, weight = FontWeight.Black),
    Font(Res.font.Poppins_Bold, weight = FontWeight.Bold),
    Font(Res.font.Poppins_ExtraBold, weight = FontWeight.ExtraBold),
    Font(Res.font.Poppins_ExtraLight, weight = FontWeight.ExtraLight),
    Font(Res.font.Poppins_Light, weight = FontWeight.Light),
    Font(Res.font.Poppins_Medium, weight = FontWeight.Medium),
    Font(Res.font.Poppins_Regular, weight = FontWeight.Normal),
    Font(Res.font.Poppins_SemiBold, weight = FontWeight.SemiBold),
    Font(Res.font.Poppins_Thin, weight = FontWeight.Thin),
)

@Composable
fun PoppinsTypography() = Typography().run {
    val fontFamily = PoppinsFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily),
    )
}