package com.plater.android.presentation.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plater.android.domain.models.ResultResource
import com.plater.android.domain.usecase.GetRecipesByMealTypeUseCase
import com.plater.android.domain.usecase.GetRecipesByTagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Shared ViewModel for managing recipe data across multiple screens.
 * Handles fetching recipes by tag and meal type, managing loading states,
 * and providing recipe lists to the UI.
 *
 * @param getRecipesByTagUseCase Use case for fetching recipes by tag
 * @param getRecipesByMealTypeUseCase Use case for fetching recipes by meal type
 */
@HiltViewModel
class RecipeSharedViewModel @Inject constructor(
    private val getRecipesByTagUseCase: GetRecipesByTagUseCase,
    private val getRecipesByMealTypeUseCase: GetRecipesByMealTypeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeUIState())
    val state = _state.asStateFlow()

    /**
     * Handles UI events related to recipe fetching.
     *
     * @param uiEvent The UI event to process (OnGetRecipesByTag or OnGetRecipesByMealType)
     */
    fun onEvent(uiEvent: RecipeUIEvents) {
        when (uiEvent) {
            is RecipeUIEvents.OnGetRecipesByTag -> {
                getRecipesByTag(
                    tag = uiEvent.tag,
                    sortBy = uiEvent.sortBy,
                    order = uiEvent.order
                )
            }

            is RecipeUIEvents.OnGetRecipesByMealType -> {
                getRecipesByMealType(
                    mealType = uiEvent.mealType,
                    sortBy = uiEvent.sortBy,
                    order = uiEvent.order
                )
            }
        }
    }

    /**
     * Fetches recipes filtered by tag.
     * Updates the state with loading, success, or error states.
     *
     * @param tag The tag to filter recipes by
     * @param sortBy Optional field to sort by (e.g., "name", "date")
     * @param order Optional sort order ("asc" or "desc")
     */
    private fun getRecipesByTag(
        tag: String,
        sortBy: String?,
        order: String?
    ) {
        viewModelScope.launch {
            getRecipesByTagUseCase(
                tag = tag,
                sortBy = sortBy,
                order = order
            ).collect { resultResource ->
                when (resultResource) {
                    is ResultResource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }

                    is ResultResource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = resultResource.message
                            )
                        }
                    }

                    is ResultResource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = null,
                                featuredList = resultResource.data
                            )
                        }
                    }
                }

            }
        }
    }

    /**
     * Fetches recipes filtered by meal type.
     * Updates the state with loading, success, or error states.
     *
     * @param mealType The meal type to filter recipes by (e.g., "breakfast", "lunch", "dinner", "snack")
     * @param sortBy Optional field to sort by (e.g., "name", "date")
     * @param order Optional sort order ("asc" or "desc")
     */
    private fun getRecipesByMealType(
        mealType: String,
        sortBy: String?,
        order: String?
    ) {
        viewModelScope.launch {
            getRecipesByMealTypeUseCase(
                mealType = mealType,
                sortBy = sortBy,
                order = order
            ).collect { resultResource ->
                when (resultResource) {
                    is ResultResource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }

                    is ResultResource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = resultResource.message
                            )
                        }
                    }

                    is ResultResource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = null,
                                mealTypeRecipeList = resultResource.data
                            )
                        }
                    }
                }

            }
        }
    }
}