package com.plater.android.domain.repository

import com.plater.android.data.remote.ApiResponse
import com.plater.android.domain.models.Recipe

/**
 * Repository interface for recipe-related data operations.
 * Abstraction over remote/local data sources that deal with recipe APIs.
 * Implementations should handle data fetching, caching, and error handling.
 */
interface RecipeRepository {

    /**
     * Fetches recipes filtered by a specific tag.
     *
     * @param tag The tag to filter recipes by (e.g., "italian", "dessert")
     * @param sortBy Optional field to sort by (e.g., "name", "date", "rating")
     * @param order Optional sort order ("asc" or "desc")
     * @return [ApiResponse] containing the list of recipes or error information
     */
    suspend fun getRecipesByTag(
        tag: String,
        sortBy: String? = null,
        order: String? = null
    ): ApiResponse<List<Recipe>>

    /**
     * Fetches recipes filtered by meal type.
     *
     * @param mealType The meal type to filter recipes by (e.g., "breakfast", "lunch", "dinner", "snack")
     * @param sortBy Optional field to sort by (e.g., "name", "date", "rating")
     * @param order Optional sort order ("asc" or "desc")
     * @return [ApiResponse] containing the list of recipes or error information
     */
    suspend fun getRecipesByMealType(
        mealType: String,
        sortBy: String? = null,
        order: String? = null
    ): ApiResponse<List<Recipe>>

}