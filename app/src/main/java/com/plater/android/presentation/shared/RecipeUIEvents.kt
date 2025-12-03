package com.plater.android.presentation.shared

sealed class RecipeUIEvents {

    data class OnGetRecipesByTag(
        val tag: String,
        val sortBy: String? = null,
        val order: String? = null
    ) : RecipeUIEvents()

    data class OnGetRecipesByMealType(
        val mealType: String,
        val sortBy: String? = null,
        val order: String? = null
    ) : RecipeUIEvents()

}