package com.plater.android.data.repositoryimpl

import android.content.Context
import com.plater.android.R
import com.plater.android.data.mappers.toDomain
import com.plater.android.data.remote.ApiResponse
import com.plater.android.data.remote.dto.response.RecipesDto
import com.plater.android.data.remote.service.ApiService
import com.plater.android.domain.models.Recipe
import com.plater.android.domain.repository.RecipeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Implementation of [RecipeRepository] that fetches recipe data from the API.
 * Handles API calls, data mapping, and error handling.
 *
 * @param apiService Retrofit service for making API calls
 * @param context Application context for accessing string resources
 */
class RecipeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) : RecipeRepository {

    /**
     * Fetches recipes filtered by tag from the API.
     * Maps DTOs to domain models and handles errors.
     *
     * @param tag The tag to filter recipes by
     * @param sortBy Optional field to sort by
     * @param order Optional sort order ("asc" or "desc")
     * @return [ApiResponse] containing the list of recipes or error information
     */
    override suspend fun getRecipesByTag(
        tag: String,
        sortBy: String?,
        order: String?
    ): ApiResponse<List<Recipe>> {
        return try {
            val response: RecipesDto = apiService.getRecipesByTag(
                tag = tag,
                sortBy = sortBy,
                order = order
            )

            val recipes = response.recipes?.filterNotNull() ?: emptyList()

            ApiResponse(
                status = true,
                message = context.getString(R.string.recipes_fetched_successfully),
                data = recipes.map { it.toDomain() }
            )

        } catch (e: Exception) {
            ApiResponse(
                status = false,
                message = e.message ?: context.getString(R.string.unknown_error_occurred),
                data = null
            )
        }
    }

    /**
     * Fetches recipes filtered by meal type from the API.
     * Maps DTOs to domain models and handles errors.
     *
     * @param mealType The meal type to filter recipes by (e.g., "breakfast", "lunch", "dinner", "snack")
     * @param sortBy Optional field to sort by
     * @param order Optional sort order ("asc" or "desc")
     * @return [ApiResponse] containing the list of recipes or error information
     */
    override suspend fun getRecipesByMealType(
        mealType: String,
        sortBy: String?,
        order: String?
    ): ApiResponse<List<Recipe>> {
        return try {
            val response: RecipesDto = apiService.getRecipesByMealType(
                mealType = mealType,
                sortBy = sortBy,
                order = order
            )

            val recipes = response.recipes?.filterNotNull() ?: emptyList()

            ApiResponse(
                status = true,
                message = context.getString(R.string.recipes_fetched_successfully),
                data = recipes.map { it.toDomain() }
            )

        } catch (e: Exception) {
            ApiResponse(
                status = false,
                message = e.message ?: context.getString(R.string.unknown_error_occurred),
                data = null
            )
        }
    }

}