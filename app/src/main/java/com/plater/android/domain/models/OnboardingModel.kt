package com.plater.android.domain.models

/**
 * Domain model representing an onboarding screen item.
 * Used to display onboarding information to new users.
 *
 * @property title Title text for the onboarding screen
 * @property description Description text explaining the feature or benefit
 * @property imageUrl URL or path to the onboarding image/illustration
 */
data class OnboardingModel(
    val title: String,
    val description: String,
    val imageUrl: String
)
