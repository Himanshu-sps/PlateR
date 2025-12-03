package com.plater.android.data.mappers

import com.plater.android.data.remote.dto.response.RecipeDto
import com.plater.android.domain.models.Recipe

fun RecipeDto.toDomain(): Recipe {
    return Recipe(
        id = this.id,
        caloriesPerServing = this.caloriesPerServing,
        cookTimeMinutes = this.cookTimeMinutes,
        cuisine = this.cuisine,
        difficulty = this.difficulty,
        image = this.image,
        ingredients = this.ingredients,
        instructions = this.instructions,
        mealType = this.mealType,
        name = this.name,
        prepTimeMinutes = this.prepTimeMinutes,
        rating = this.rating,
        reviewCount = this.reviewCount,
        servings = this.servings,
        tags = this.tags,
        userId = this.userId
    )
}

fun Recipe.toDto(): RecipeDto {
    return RecipeDto(
        id = this.id,
        caloriesPerServing = this.caloriesPerServing,
        cookTimeMinutes = this.cookTimeMinutes,
        cuisine = this.cuisine,
        difficulty = this.difficulty,
        image = this.image,
        ingredients = this.ingredients,
        instructions = this.instructions,
        mealType = this.mealType,
        name = this.name,
        prepTimeMinutes = this.prepTimeMinutes,
        rating = this.rating,
        reviewCount = this.reviewCount,
        servings = this.servings,
        tags = this.tags,
        userId = this.userId
    )
}