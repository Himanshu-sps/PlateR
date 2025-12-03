package com.plater.android.domain.usecase

import android.content.Context
import com.plater.android.R
import com.plater.android.data.remote.ApiResponse
import com.plater.android.domain.models.Recipe
import com.plater.android.domain.models.ResultResource
import com.plater.android.domain.repository.RecipeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case for fetching recipes filtered by meal type.
 * Coordinates the API call, wraps responses into [ResultResource] for the UI,
 * and handles error cases.
 *
 * @param recipeRepository Repository for recipe data operations
 * @param context Application context for accessing string resources
 */
class GetRecipesByMealTypeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository,
    @ApplicationContext private val context: Context
) {

    /**
     * Fetches recipes filtered by meal type and emits loading, success, or error states.
     *
     * @param mealType The meal type to filter recipes by (e.g., "breakfast", "lunch", "dinner", "snack")
     * @param sortBy Optional field to sort by (e.g., "name", "date")
     * @param order Optional sort order ("asc" or "desc")
     * @return Flow of [ResultResource] containing the list of recipes or error state
     */
    operator fun invoke(
        mealType: String,
        sortBy: String? = null,
        order: String? = null
    ): Flow<ResultResource<List<Recipe>>> = flow {
        emit(ResultResource.Loading)

        try {
            val apiResponse: ApiResponse<List<Recipe>> = recipeRepository.getRecipesByMealType(
                mealType = mealType,
                sortBy = sortBy,
                order = order
            )

            if (apiResponse.status) {
                emit(
                    ResultResource.Success(
                        message = apiResponse.message
                            ?: context.getString(R.string.something_went_wrong),
                        data = apiResponse.data ?: emptyList()
                    )
                )
            } else {
                emit(
                    ResultResource.Error(
                        message = apiResponse.message
                            ?: context.getString(R.string.something_went_wrong)
                    )
                )
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                ResultResource.Error(
                    message = e.message ?: context.getString(R.string.unknown_error_occurred),
                    cause = e
                )
            )
        }
    }
}