package com.plater.android.presentation.shared

import com.plater.android.domain.models.Recipe

data class RecipeUIState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val featuredList: List<Recipe> = emptyList(),
    val mealTypeRecipeList: List<Recipe> = emptyList()
)