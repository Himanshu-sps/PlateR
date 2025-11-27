package com.plater.android.presentation.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.domain.models.OnboardingModel
import com.plater.android.presentation.common.AppButton
import com.plater.android.presentation.common.ButtonType
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onSkipClick: () -> Unit = {}
) {
    val onboardingItems = remember {
        listOf(
            OnboardingModel(
                title = "Discover Delicious Recipes",
                description = "Explore a world of curated recipes tailored to your taste — from breakfast ideas to gourmet dinners.",
                imageUrl = "https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=900&q=60"
            ),
            OnboardingModel(
                title = "Save Your Favorites",
                description = "Keep all your favorite dishes in one place and access them even when you're offline.",
                imageUrl = "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?auto=format&fit=crop&w=900&q=60"
            ),
            OnboardingModel(
                title = "Step-by-Step Guidance",
                description = "Follow easy step-by-step instructions with visuals to make cooking simple and enjoyable.",
                imageUrl = "https://images.unsplash.com/photo-1510626176961-4b57d4fbad03?auto=format&fit=crop&w=900&q=60"
            ),
            OnboardingModel(
                title = "Sync & Go Offline",
                description = "Stay connected when online or offline — your recipes sync automatically in the background.",
                imageUrl = "https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=900&q=60"
            )
        )
    }

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { onboardingItems.size })
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            OnBoardItem(onboardingModel = onboardingItems[page])
        }

        Box(
            modifier = Modifier
                .padding(top = DimensUtils.dimenDp(R.dimen.size_16))
                .align(Alignment.TopEnd)
        ) {
            AppButton(
                text = stringResource(R.string.skip),
                type = ButtonType.Text,
                rightIcon = painterResource(R.drawable.ic_arrow_right),
                textStyle = MaterialTheme.typography.labelSmall,
                onClick = {
                    onSkipClick.invoke()
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DimensUtils.dimenDp(R.dimen.size_16))
                .align(Alignment.BottomEnd),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            //Indicator
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                repeat(onboardingItems.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .width(if (isSelected) 18.dp else 8.dp)
                            .height(if (isSelected) 8.dp else 8.dp)
                            .border(
                                width = DimensUtils.dimenDp(R.dimen.size_1),
                                color = colorScheme.primary,
                                shape = RoundedCornerShape(DimensUtils.dimenDp(R.dimen.size_8))
                            )
                            .background(
                                color = if (isSelected) colorScheme.tertiary else colorScheme.onPrimary,
                                shape = CircleShape
                            )
                    )
                }
            }

            val isLastPage = pagerState.currentPage == onboardingItems.lastIndex
            val nextLabel =
                if (isLastPage) stringResource(R.string.start_cooking) else stringResource(
                    R.string.next
                )

            AppButton(
                text = nextLabel,
                rightIcon = painterResource(R.drawable.ic_arrow_right),
                onClick = {
                    coroutineScope.launch {
                        if (isLastPage) {
                            onSkipClick()
                        } else {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun OnBoardItem(
    modifier: Modifier = Modifier,
    onboardingModel: OnboardingModel
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f)
                .padding(DimensUtils.dimenDp(R.dimen.size_8))
                .shadow(
                    elevation = DimensUtils.dimenDp(R.dimen.size_32),
                    shape = CircleShape,
                    ambientColor = colorScheme.primary,
                    spotColor = colorScheme.primary,
                    clip = false
                )
                .border(
                    width = DimensUtils.dimenDp(R.dimen.size_2),
                    color = colorScheme.primary,
                    shape = CircleShape
                )
                .clip(shape = CircleShape)
        ) {
            AsyncImage(
                modifier = Modifier.matchParentSize(),
                model = onboardingModel.imageUrl,
                contentDescription = onboardingModel.title,
                contentScale = ContentScale.Crop
            )
        }


        Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_16)))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = onboardingModel.title,
            style = MaterialTheme.typography.bodyLarge,
            color = colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_8)))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "\"${onboardingModel.description}\"",
            style = MaterialTheme.typography.labelSmall,
            color = colorScheme.onBackground,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic
        )

    }
}
