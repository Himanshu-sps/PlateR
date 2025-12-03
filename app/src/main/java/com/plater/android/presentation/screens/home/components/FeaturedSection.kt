package com.plater.android.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.domain.models.Recipe
import com.plater.android.presentation.common.RecipeCardItem

/**
 * Composable section that displays featured recipes in a horizontal scrollable list.
 * Shows a "Featured" title and a LazyRow of recipe cards.
 *
 * @param recipes List of featured recipes to display
 * @param modifier Modifier to apply to the section container
 */
@Composable
fun FeaturedSection(
    recipes: List<Recipe>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(horizontal = DimensUtils.dimenDp(R.dimen.size_8)),
            text = stringResource(R.string.featured),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_8)))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = DimensUtils.dimenDp(R.dimen.size_8)
            ),
            horizontalArrangement = Arrangement.spacedBy(DimensUtils.dimenDp(R.dimen.size_4))
        ) {
            items(
                items = recipes,
                key = { recipe -> recipe.id ?: recipe.hashCode() }
            ) { recipe ->
                RecipeCardItem(recipe = recipe)
            }
        }
    }
}