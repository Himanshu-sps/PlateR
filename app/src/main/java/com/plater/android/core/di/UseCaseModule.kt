package com.plater.android.core.di

import android.content.Context
import com.plater.android.domain.repository.RecipeRepository
import com.plater.android.domain.usecase.GetRecipesByMealTypeUseCase
import com.plater.android.domain.usecase.GetRecipesByTagUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetRecipesByTagUseCase(
        recipeRepository: RecipeRepository,
        @ApplicationContext context: Context
    ): GetRecipesByTagUseCase {
        return GetRecipesByTagUseCase(
            recipeRepository = recipeRepository,
            context = context
        )
    }

    @Provides
    fun provideGetRecipesByMealTypeUseCase(
        recipeRepository: RecipeRepository,
        @ApplicationContext context: Context
    ): GetRecipesByMealTypeUseCase {
        return GetRecipesByMealTypeUseCase(
            recipeRepository = recipeRepository,
            context = context
        )
    }
}