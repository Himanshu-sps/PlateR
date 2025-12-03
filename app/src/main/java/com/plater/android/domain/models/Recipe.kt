package com.plater.android.domain.models

/**
 * Domain model representing a recipe in the application.
 * Contains all recipe-related information including ingredients, instructions, nutritional info, and metadata.
 *
 * @property id Unique identifier for the recipe
 * @property caloriesPerServing Number of calories per serving
 * @property cookTimeMinutes Cooking time in minutes
 * @property cuisine Cuisine type (e.g., "Italian", "Mexican")
 * @property difficulty Difficulty level (e.g., "Easy", "Medium", "Hard")
 * @property image URL or path to the recipe image
 * @property ingredients List of ingredients required for the recipe
 * @property instructions List of cooking instructions/steps
 * @property mealType List of meal types this recipe belongs to (e.g., ["breakfast", "lunch"])
 * @property name Name of the recipe
 * @property prepTimeMinutes Preparation time in minutes
 * @property rating Average rating (0.0 to 5.0)
 * @property reviewCount Number of reviews/ratings
 * @property servings Number of servings the recipe yields
 * @property tags List of tags associated with the recipe (e.g., ["vegetarian", "gluten-free"])
 * @property userId ID of the user who created the recipe
 */
data class Recipe(
    val id: Int? = null,
    val caloriesPerServing: Int? = null,
    val cookTimeMinutes: Int? = null,
    val cuisine: String? = null,
    val difficulty: String? = null,
    val image: String? = null,
    val ingredients: List<String?>? = null,
    val instructions: List<String?>? = null,
    val mealType: List<String?>? = null,
    val name: String? = null,
    val prepTimeMinutes: Int? = null,
    val rating: Double? = null,
    val reviewCount: Int? = null,
    val servings: Int? = null,
    val tags: List<String?>? = null,
    val userId: Int? = null
)
