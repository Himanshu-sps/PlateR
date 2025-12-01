package com.plater.android.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils

/**
 * Reusable rating component that displays a 1-5 star rating.
 *
 * @param rating The rating value (0.0 to 5.0)
 * @param modifier Modifier to be applied to the rating component
 * @param starSize Size of each star icon
 * @param spacing Spacing between stars
 * @param filledStarColor Color for filled stars
 * @param emptyStarColor Color for empty stars
 */
@Composable
fun RatingComponent(
    rating: Double,
    modifier: Modifier = Modifier,
    starSize: Dp = DimensUtils.dimenDp(R.dimen.size_12),
    spacing: Dp = DimensUtils.dimenDp(R.dimen.size_2),
    filledStarColor: Color = MaterialTheme.colorScheme.secondary,
    emptyStarColor: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
) {

    val clampedRating = rating.coerceIn(0.0, 5.0)
    val fullStars = clampedRating.toInt()
    val hasHalfStar = (clampedRating - fullStars) >= 0.5
    val emptyStars = 5 - fullStars - if (hasHalfStar) 1 else 0

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.padding(horizontal = DimensUtils.dimenDp(R.dimen.size_8)),
            text = "$rating",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        // Filled stars
        repeat(fullStars) {
            Icon(
                painter = painterResource(id = R.drawable.ic_star_filled),
                contentDescription = "Filled star",
                modifier = Modifier.size(starSize),
                tint = filledStarColor
            )
        }

        // Half star (optional - you can remove this if you don't want half stars)
        // For now, we'll show it as filled if >= 0.5, empty otherwise
        if (hasHalfStar) {
            Icon(
                painter = painterResource(id = R.drawable.ic_star_filled),
                contentDescription = "Half star",
                modifier = Modifier.size(starSize),
                tint = filledStarColor.copy(alpha = 0.5f)
            )
        }

        // Empty stars
        repeat(emptyStars) {
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "Empty star",
                modifier = Modifier.size(starSize),
                tint = emptyStarColor
            )
        }
    }
}

