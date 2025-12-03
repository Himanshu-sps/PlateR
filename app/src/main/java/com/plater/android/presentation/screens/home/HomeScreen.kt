package com.plater.android.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.domain.models.Recipe
import com.plater.android.presentation.common.AppLoader
import com.plater.android.presentation.common.AppSelectableChipsList
import com.plater.android.presentation.common.RecipeGridItem
import com.plater.android.presentation.screens.home.components.FeaturedSection
import com.plater.android.presentation.screens.home.components.UserGreetingSection
import com.plater.android.presentation.shared.RecipeSharedViewModel
import com.plater.android.presentation.shared.RecipeUIEvents

/**
 * Main home screen composable that displays:
 * - User greeting section
 * - Featured recipes section (Italian recipes)
 * - Meal type selection chips (sticky header)
 * - Recipe grid based on selected meal type
 *
 * The screen fetches two sets of data simultaneously on initial load:
 * 1. Featured recipes by tag (Italian)
 * 2. Recipes by meal type (default: Snack)
 *
 * @param viewModel The HomeViewModel instance for user data (injected via Hilt)
 * @param recipeSharedViewModel The shared ViewModel for recipe data management
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    recipeSharedViewModel: RecipeSharedViewModel
) {
    val userState by viewModel.userState.collectAsState()
    val user = userState?.user

    // Access shared recipe data from RecipeSharedViewModel
    val sharedRecipeState by recipeSharedViewModel.state.collectAsState()

    var selectedMealType by remember {
        mutableStateOf(MealType.SNACK.displayName.lowercase())
    }

    // Fetch featured recipes on initial load
    LaunchedEffect(key1 = Unit) {
        // Fetching featured list
        recipeSharedViewModel.onEvent(
            RecipeUIEvents.OnGetRecipesByTag(
                tag = "italian",
                sortBy = "name",
                order = "asc"
            )
        )
    }

    // Fetch recipes by meal type - runs on initial load and when meal type changes
    LaunchedEffect(key1 = selectedMealType) {
        if (selectedMealType.isNotBlank()) {
            recipeSharedViewModel.onEvent(
                RecipeUIEvents.OnGetRecipesByMealType(
                    mealType = selectedMealType,
                    sortBy = "name",
                    order = "asc"
                )
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            contentPadding = PaddingValues(
                vertical = DimensUtils.dimenDp(R.dimen.size_10)
            )
        ) {
            // User greeting section
            item {
                UserGreetingSection(
                    modifier = Modifier.padding(horizontal = DimensUtils.dimenDp(R.dimen.size_8)),
                    user = user
                )
            }

            // featured recipe list
            item {
                Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_40)))
            }

            if (sharedRecipeState.featuredList.isNotEmpty()) {
                item {
                    FeaturedSection(
                        recipes = sharedRecipeState.featuredList,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_16)))
            }

            // Sticky header for "Choose your meal" section
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(
                            horizontal = DimensUtils.dimenDp(R.dimen.size_8),
                            vertical = DimensUtils.dimenDp(R.dimen.size_8)
                        )
                ) {
                    Text(
                        text = stringResource(R.string.choose_your_meal),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_8)))

                    AppSelectableChipsList(
                        items = MealType.entries,
                        onItemSelected = { mealType ->
                            selectedMealType = mealType.displayName.lowercase()
                        },
                        getDisplayText = { it.displayName },
                        defaultSelectedIndex = 0 // Default to Breakfast
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_16)))
            }

            // Recipe Grid Section
            if (sharedRecipeState.mealTypeRecipeList.isNotEmpty()) {
                item {
                    // Recipe Grid using FlowRow for natural wrapping
                    RecipeGridSection(
                        recipes = sharedRecipeState.mealTypeRecipeList,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Show loader when loading
        if (sharedRecipeState.isLoading) {
            AppLoader()
        }
    }
}


/**
 * Composable that displays a grid of recipe items in a 2-column layout.
 * Uses FlowRow to automatically wrap items to the next row when needed.
 *
 * The grid calculates item width dynamically to ensure exactly 2 items fit per row,
 * accounting for spacing between items.
 *
 * @param recipes The list of recipes to display in the grid
 * @param modifier Optional modifier to apply to the grid container
 */
@Composable
private fun RecipeGridSection(
    recipes: List<Recipe>,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier.padding(horizontal = DimensUtils.dimenDp(R.dimen.size_8))
    ) {
        // Calculate item width to ensure minimum 2 items per row
        val itemSpacing = DimensUtils.dimenDp(R.dimen.size_8)
        val availableWidth = maxWidth
        // Calculate width so exactly 2 items fit: (availableWidth - itemSpacing) / 2
        val itemWidth = (availableWidth - itemSpacing) / 2

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(itemSpacing),
            verticalArrangement = Arrangement.spacedBy(DimensUtils.dimenDp(R.dimen.size_12)),
            maxItemsInEachRow = 2
        ) {
            recipes.forEach { recipe ->
                RecipeGridItem(
                    recipe = recipe,
                    modifier = Modifier
                        .width(itemWidth)
                        .height(DimensUtils.dimenDp(R.dimen.size_200))
                )
            }
        }
    }
}

/**
 * Enum representing different meal types available in the app.
 * Used for filtering recipes by meal type.
 *
 * @property displayName The human-readable name of the meal type
 */
enum class MealType(val displayName: String) {
    /** Snack meal type */
    SNACK("Snack"),

    /** Breakfast meal type */
    BREAKFAST("Breakfast"),

    /** Lunch meal type */
    LUNCH("Lunch"),

    /** Dinner meal type */
    DINNER("Dinner")
}