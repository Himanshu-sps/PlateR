package com.plater.android.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils

/**
 * Generic reusable component for displaying selectable chips in a LazyRow.
 *
 * @param items List of items to display as chips
 * @param selectedItem Currently selected item (can be null)
 * @param onItemSelected Callback when an item is selected
 * @param getDisplayText Function to extract display text from an item
 * @param modifier Modifier for the LazyRow
 * @param contentPadding Padding for the LazyRow content
 * @param horizontalArrangement Spacing between chips
 * @param defaultSelectedIndex Index of item to select by default (if selectedItem is null)
 */
@Composable
fun <T> AppSelectableChipsList(
    modifier: Modifier = Modifier,
    items: List<T>,
    selectedItem: T? = null,
    onItemSelected: (T) -> Unit,
    getDisplayText: (T) -> String,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Normal
    ),
    contentPadding: PaddingValues = PaddingValues(horizontal = DimensUtils.dimenDp(R.dimen.size_8)),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(DimensUtils.dimenDp(R.dimen.size_8)),
    defaultSelectedIndex: Int = 0
) {
    // Internal state if no selectedItem is provided
    var internalSelectedItem by remember {
        mutableStateOf<T?>(
            if (items.isNotEmpty() && defaultSelectedIndex < items.size) {
                items[defaultSelectedIndex]
            } else {
                null
            }
        )
    }

    // Use provided selectedItem or internal state
    val currentSelected = selectedItem ?: internalSelectedItem

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement
    ) {
        items(items = items, key = { item -> item.hashCode() }) { item ->
            val isSelected = currentSelected == item

            AppButton(
                text = getDisplayText(item),
                type = if (isSelected) ButtonType.Primary else ButtonType.Outlined,
                primaryColor = MaterialTheme.colorScheme.primary,
                textStyle = textStyle,
                cornerRadiusPercent = 50,
                horizontalPadding = DimensUtils.dimenDp(R.dimen.size_12),
                onClick = {
                    if (selectedItem == null) {
                        internalSelectedItem = item
                    }
                    onItemSelected(item)
                }
            )
        }
    }
}

