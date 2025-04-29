package com.hse.courseworkcompose.presentation.animation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hse.courseworkcompose.R

@Composable
fun LoadingAnimation(
    isDarkTheme: Boolean
) {
    val animation = if (isDarkTheme) LottieCompositionSpec.RawRes(R.raw.animation_loading_dark) else LottieCompositionSpec.RawRes(R.raw.animation_loading_light)

    val composition by rememberLottieComposition(animation)
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever // Бесконечная анимация
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(300.dp).background(Color.Transparent)
    )
}