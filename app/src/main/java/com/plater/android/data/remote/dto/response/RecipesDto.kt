package com.plater.android.data.remote.dto.response


import com.google.gson.annotations.SerializedName

data class RecipesDto(
    @SerializedName("limit")
    val limit: Int? = null,
    @SerializedName("recipes")
    val recipes: List<RecipeDto?>? = null,
    @SerializedName("skip")
    val skip: Int? = null,
    @SerializedName("total")
    val total: Int? = null
)

data class RecipeDto(
    @SerializedName("caloriesPerServing")
    val caloriesPerServing: Int? = null,
    @SerializedName("cookTimeMinutes")
    val cookTimeMinutes: Int? = null,
    @SerializedName("cuisine")
    val cuisine: String? = null,
    @SerializedName("difficulty")
    val difficulty: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("ingredients")
    val ingredients: List<String?>? = null,
    @SerializedName("instructions")
    val instructions: List<String?>? = null,
    @SerializedName("mealType")
    val mealType: List<String?>? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("prepTimeMinutes")
    val prepTimeMinutes: Int? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("reviewCount")
    val reviewCount: Int? = null,
    @SerializedName("servings")
    val servings: Int? = null,
    @SerializedName("tags")
    val tags: List<String?>? = null,
    @SerializedName("userId")
    val userId: Int? = null
)