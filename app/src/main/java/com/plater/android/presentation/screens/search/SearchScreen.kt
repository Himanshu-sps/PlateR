package com.plater.android.presentation.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.presentation.shared.RecipeSharedViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    recipeSharedViewModel: RecipeSharedViewModel
) {
    // Example: Access shared recipe data from RecipeSharedViewModel
    // val sharedRecipeState by recipeSharedViewModel.someState.collectAsState()
    // val searchResults = recipeSharedViewModel.searchResults

    // Example: Call methods on shared ViewModel
    // recipeSharedViewModel.searchRecipes(query)
    // recipeSharedViewModel.selectRecipe(recipeId)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = DimensUtils.dimenDp(R.dimen.size_16),
                vertical = DimensUtils.dimenDp(R.dimen.size_24)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Search",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

