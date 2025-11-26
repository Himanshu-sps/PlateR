package com.plater.android.presentation.screens.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.presentation.uiresources.NeutralWhite

@Preview(showBackground = false)
@Composable
fun SplashScreen(modifier: Modifier = Modifier) {

    val transition = rememberInfiniteTransition(label = "splash_transition")
    val iconScale by transition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "icon_scale"
    )

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_splash_screen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    all = DimensUtils.dimenDp(R.dimen.size_4)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_chef_cap),
                    contentDescription = null,
                    modifier = Modifier
                        .size(
                            DimensUtils.dimenDp(R.dimen.size_79)
                        )
                        .graphicsLayer {
                            scaleX = iconScale
                            scaleY = iconScale
                        },
                    tint = NeutralWhite
                )

                Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_26)))

                Text(
                    text = stringResource(R.string.plater),
                    style = MaterialTheme.typography.headlineLarge,
                    color = NeutralWhite,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_8)))

                Text(
                    text = stringResource(R.string.simple_way_to_find_tasty_recipe),
                    style = MaterialTheme.typography.labelMedium,
                    color = NeutralWhite,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

