package com.plater.android.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.zIndex
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.domain.models.Recipe

/**
 * Composable that displays a recipe card item in a horizontal list.
 * Shows recipe image, name, rating, cooking time, difficulty, and tags.
 * Designed for use in horizontal scrollable lists (e.g., FeaturedSection).
 *
 * @param recipe The recipe data to display
 */
@Composable
fun RecipeCardItem(
    recipe: Recipe
) {
    val color = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .width(DimensUtils.dimenDp(R.dimen.size_225))
            .height(DimensUtils.dimenDp(R.dimen.size_120))
            .background(color = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .zIndex(1.0f)
                .align(Alignment.TopStart)
                .offset(x = DimensUtils.dimenDp(R.dimen.size_20))
                .dropShadow(
                    shape = CircleShape,
                    shadow = Shadow(
                        radius = DimensUtils.dimenDp(R.dimen.size_1),
                        spread = DimensUtils.dimenDp(R.dimen.size_0),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.15f),
                    )
                )
        ) {
            if (!recipe?.image.isNullOrBlank()) {
                val imagePainter = rememberAsyncImagePainter(model = recipe?.image)
                val imageState = imagePainter.state

                Box(
                    modifier = Modifier
                        .size(DimensUtils.dimenDp(R.dimen.size_60))
                        .clip(CircleShape)
                        .background(color = color.primary)
                ) {
                    Image(
                        painter = imagePainter,
                        contentDescription = "recipe image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Show smaller placeholder icon while loading or on error
                    if (imageState is AsyncImagePainter.State.Loading ||
                        imageState is AsyncImagePainter.State.Error
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_chef_cap),
                                contentDescription = if (imageState is AsyncImagePainter.State.Loading) "Loading" else "Error",
                                modifier = Modifier.size(DimensUtils.dimenDp(R.dimen.size_50)),
                                tint = color.onPrimary
                            )
                        }
                    }
                }
            } else {
                Icon(
                    modifier = Modifier
                        .size(DimensUtils.dimenDp(R.dimen.size_50))
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

        ElevatedCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = DimensUtils.dimenDp(R.dimen.size_30))
                .padding(horizontal = DimensUtils.dimenDp(R.dimen.size_4))
                .dropShadow(
                    shape = RoundedCornerShape(percent = 10),
                    shadow = Shadow(
                        radius = DimensUtils.dimenDp(R.dimen.size_8),
                        spread = DimensUtils.dimenDp(R.dimen.size_0),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f),
                    )
                ),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            shape = RoundedCornerShape(percent = 10)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = DimensUtils.dimenDp(R.dimen.size_6))
            ) {
                //Rating Component with Review Count - Top Right
                if (recipe?.rating != null) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopEnd),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(DimensUtils.dimenDp(R.dimen.size_2))
                    ) {
                        RatingComponent(
                            rating = recipe.rating,
                            modifier = Modifier
                        )

                        //Review Count
                        if (recipe.reviewCount != null && recipe.reviewCount > 0) {

                            Text(
                                text = "(${recipe.reviewCount} reviews)",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                //Content at Bottom
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart),
                    verticalArrangement = Arrangement.spacedBy(DimensUtils.dimenDp(R.dimen.size_4))
                ) {
                    //Recipe Name
                    Text(
                        text = recipe?.name ?: "Recipe Name",
                        color = color.onBackground,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Timer on left
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(DimensUtils.dimenDp(R.dimen.size_4))
                        ) {
                            Icon(
                                modifier = Modifier.size(DimensUtils.dimenDp(R.dimen.size_12)),
                                painter = painterResource(R.drawable.ic_timer),
                                tint = color.onSurfaceVariant,
                                contentDescription = "timer"
                            )

                            Text(
                                text = if (recipe?.cookTimeMinutes != null) {
                                    "${recipe.cookTimeMinutes} mins"
                                } else if (recipe?.prepTimeMinutes != null) {
                                    "${recipe.prepTimeMinutes} mins"
                                } else {
                                    "N/A"
                                },
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Cuisine box on right
                        if (!recipe?.cuisine.isNullOrBlank()) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(DimensUtils.dimenDp(R.dimen.size_4)))
                                    .background(
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    .padding(
                                        horizontal = DimensUtils.dimenDp(R.dimen.size_6),
                                        vertical = DimensUtils.dimenDp(R.dimen.size_4)
                                    )
                            ) {
                                Text(
                                    text = recipe.cuisine,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

