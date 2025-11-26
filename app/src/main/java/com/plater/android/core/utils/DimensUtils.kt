package com.plater.android.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Utility class for accessing dimension resources (dp and sp) in Jetpack Compose.
 *
 * This utility provides convenient methods to access dimension resources from XML files.
 *
 * Usage examples:
 * - For dp dimensions: `DimensUtils.dimenDp(R.dimen.size_16)`
 * - For sp dimensions: `DimensUtils.dimenSp(R.dimen.text_size_14)`
 * - Using extension functions: `R.dimen.size_16.dp()` or `R.dimen.text_size_14.sp()`
 */
object DimensUtils {

    /**
     * Gets a dimension resource as Dp.
     * Use this for layout dimensions (padding, margins, sizes, etc.)
     *
     * Note: dimensionResource() always returns Dp, regardless of whether
     * the resource is defined in dp or sp in XML.
     *
     * @param resId The resource ID of the dimension (e.g., R.dimen.size_16)
     * @return The dimension value as Dp
     */
    @Composable
    fun dimenDp(resId: Int): Dp {
        return dimensionResource(id = resId)
    }

    /**
     * Gets a dimension resource as TextUnit (sp).
     * Use this for text sizes.
     *
     * This method converts the dimension resource to sp units.
     * Compose will automatically handle font scaling when using sp units.
     *
     * Note: If the resource is defined in dp, it will be converted to sp.
     * For text sizes, it's recommended to define resources in sp units in XML.
     *
     * @param resId The resource ID of the dimension (e.g., R.dimen.text_size_14)
     * @return The dimension value as TextUnit (sp)
     */
    @Composable
    fun dimenSp(resId: Int): TextUnit {
        val dpValue = dimensionResource(id = resId)
        // Convert Dp to sp (Compose handles font scaling automatically)
        return dpValue.value.sp
    }

    /**
     * Extension function for Int resource IDs to get Dp values.
     *
     * Usage: `R.dimen.size_16.dp()`
     */
    @Composable
    fun Int.dp(): Dp {
        return dimenDp(this)
    }

    /**
     * Extension function for Int resource IDs to get sp values.
     *
     * Usage: `R.dimen.text_size_14.sp()`
     */
    @Composable
    fun Int.sp(): TextUnit {
        return dimenSp(this)
    }
}

/**
 * Extension function to get a non-scaled TextUnit that ignores system font size settings.
 *
 * This cancels out the system font scaling by dividing by the font scale factor,
 * ensuring typography remains fixed regardless of user's font size preference.
 *
 * Usage:
 * ```
 * // Get dimension and make it non-scaled
 * val fontSize = DimensUtils.dimenSp(R.dimen.size_16).nonScaledSp
 *
 * // Or chain it directly
 * Text(
 *     text = "Hello",
 *     fontSize = DimensUtils.dimenSp(R.dimen.size_16).nonScaledSp
 * )
 * ```
 *
 * @return TextUnit that ignores system font scaling
 */
val TextUnit.nonScaledSp: TextUnit
    @Composable
    get() = (this.value / LocalDensity.current.fontScale).sp

