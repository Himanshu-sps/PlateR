package com.plater.android.data.remote.service

import com.plater.android.data.remote.dto.response.RecipesDto
import com.plater.android.data.remote.dto.response.UserDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit definitions for authentication endpoints. It will use all apis with Bearer <TOKEN> headers
 */
interface ApiService {

    companion object {
        const val GET_CURRENT_USER_ENDPOINT = "auth/me"
        const val GET_RECIPES_BY_TAG_ENDPOINT = "recipes/tag/{tag}"
        const val GET_RECIPES_BY_MEAL_TYPE_ENDPOINT = "recipes/meal-type/{mealType}"
    }

    /**
     * Gets the current authenticated user's information.
     */
    @GET(GET_CURRENT_USER_ENDPOINT)
    suspend fun getCurrentUser(): UserDto

    @GET(GET_RECIPES_BY_TAG_ENDPOINT)
    suspend fun getRecipesByTag(
        @Path("tag") tag: String,
        @Query("sortBy") sortBy: String? = null,
        @Query("order") order: String? = null
    ): RecipesDto

    @GET(GET_RECIPES_BY_MEAL_TYPE_ENDPOINT)
    suspend fun getRecipesByMealType(
        @Path("mealType") mealType: String,
        @Query("sortBy") sortBy: String? = null,
        @Query("order") order: String? = null
    ): RecipesDto

}