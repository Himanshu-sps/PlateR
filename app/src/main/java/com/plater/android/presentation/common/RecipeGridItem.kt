package com.plater.android.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.domain.models.Recipe

/**
 * Composable that displays a single recipe item in a grid layout.
 * Shows recipe image (or default icon), name, cooking time, and bookmark icon.
 * Designed for use in a 2-column grid layout.
 *
 * @param modifier Modifier to apply to the recipe item container
 * @param recipe The recipe data to display. If null, shows placeholder content
 */
@Composable
fun RecipeGridItem(
    modifier: Modifier = Modifier,
    recipe: Recipe? = null,
) {
    val color = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .defaultMinSize(minWidth = DimensUtils.dimenDp(R.dimen.size_150))
            .height(DimensUtils.dimenDp(R.dimen.size_230))
            .background(color = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .zIndex(1.0f)
                .align(Alignment.TopCenter)
        ) {

            if (!recipe?.image.isNullOrBlank()) {
                AsyncImage(
                    modifier = Modifier
                        .size(DimensUtils.dimenDp(R.dimen.size_80))
                        .clip(CircleShape)
                        .background(
                            color = color.secondary
                        ),
                    model = recipe.image,
                    contentDescription = "recipe image",
                    contentScale = ContentScale.Fit
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(DimensUtils.dimenDp(R.dimen.size_80))
                        .clip(CircleShape)
                        .background(
                            color = color.primary
                        )
                        .padding(DimensUtils.dimenDp(R.dimen.size_8)),
                    painter = painterResource(R.drawable.ic_chef_cap),
                    contentDescription = "",
                    tint = color.onPrimary
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = DimensUtils.dimenDp(R.dimen.size_40))
                .background(
                    color = color.surfaceVariant.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(percent = 10)
                )
                .padding(DimensUtils.dimenDp(R.dimen.size_8))
        ) {

            Text(
                modifier = Modifier.align(alignment = Alignment.Center),
                text = recipe?.name ?: "Recipe Name",
                color = color.onBackground,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = DimensUtils.dimenSp(R.dimen.size_20)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                Column {
                    Text(
                        text = "Time",
                        color = color.onSurfaceVariant,
                        style = MaterialTheme.typography.labelMedium,
                    )

                    Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_4)))

                    Text(
                        text = if (recipe?.cookTimeMinutes != null) {
                            "${recipe.cookTimeMinutes} mins"
                        } else if (recipe?.prepTimeMinutes != null) {
                            "${recipe.prepTimeMinutes} mins"
                        } else {
                            "N/A"
                        },
                        color = color.onBackground,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = color.background)
                        .padding(DimensUtils.dimenDp(R.dimen.size_8)),
                ) {
                    Icon(
                        modifier = Modifier.size(DimensUtils.dimenDp(R.dimen.size_16)),
                        painter = painterResource(R.drawable.ic_bookmark),
                        tint = color.primary,
                        contentDescription = "bookmark"
                    )
                }
            }
        }

    }
}

