package com.plater.android.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils

/**
 * Enum representing different button styles available in the app.
 */
enum class ButtonType {
    /** Primary button with filled background */
    Primary,

    /** Outlined button with border and transparent background */
    Outlined,

    /** Text-only button with no background or border */
    Text,

    /** Icon-only button with circular background */
    IconOnly
}

/**
 * Reusable button component that supports multiple styles and configurations.
 * Can display text, icons, or both, with customizable colors and styling.
 *
 * @param modifier Modifier to apply to the button
 * @param text Optional text to display on the button
 * @param type The button style type (Primary, Outlined, Text, or IconOnly)
 * @param leftIcon Optional icon to display on the left side of the text
 * @param rightIcon Optional icon to display on the right side of the text
 * @param onlyIcon Optional icon for IconOnly button type
 * @param enabled Whether the button is enabled and clickable
 * @param iconSize Size of the icons
 * @param primaryColor Primary color for the button
 * @param cornerRadiusPercent Corner radius as percentage (0-100)
 * @param textStyle Text style for the button text
 * @param horizontalPadding Horizontal padding inside the button
 * @param verticalPadding Vertical padding inside the button
 * @param onClick Callback invoked when the button is clicked
 */
@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    type: ButtonType = ButtonType.Primary,
    leftIcon: ImageVector? = null,
    rightIcon: Painter? = null,
    onlyIcon: ImageVector? = null,
    enabled: Boolean = true,
    iconSize: Dp = DimensUtils.dimenDp(R.dimen.size_16),
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    cornerRadiusPercent: Int = 20,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium.copy(
        fontWeight = FontWeight.SemiBold
    ),
    horizontalPadding: Dp = DimensUtils.dimenDp(R.dimen.size_6),
    verticalPadding: Dp = DimensUtils.dimenDp(R.dimen.size_8),
    onClick: () -> Unit = {}
) {

    val bgColor: Color
    var contentColor: Color = MaterialTheme.colorScheme.onPrimary
    val border: BorderStroke?

    when (type) {

        ButtonType.Primary -> {
            bgColor = if (enabled) primaryColor else primaryColor.copy(alpha = 0.4f)
            contentColor = if (enabled) contentColor else contentColor.copy(alpha = 0.4f)
            border = null
        }

        ButtonType.Outlined -> {
            bgColor = Color.Transparent
            contentColor = if (enabled) primaryColor else primaryColor.copy(alpha = 0.4f)
            border = BorderStroke(DimensUtils.dimenDp(R.dimen.size_1), contentColor)
        }

        ButtonType.Text -> {
            bgColor = Color.Transparent
            contentColor = if (enabled) primaryColor else primaryColor.copy(alpha = 0.4f)
            border = null
        }

        ButtonType.IconOnly -> {
            bgColor = if (enabled) primaryColor else primaryColor.copy(alpha = 0.4f)
            contentColor = if (enabled) contentColor else contentColor.copy(alpha = 0.4f)
            border = null
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(percent = cornerRadiusPercent))
            .background(bgColor)
            .then(
                if (border != null) Modifier.border(
                    border,
                    RoundedCornerShape(percent = cornerRadiusPercent)
                )
                else Modifier
            )
            .clickable(enabled = enabled) { onClick() }
            .padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding
            ),
        contentAlignment = Alignment.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            if (type == ButtonType.IconOnly && onlyIcon != null) {
                Icon(
                    imageVector = onlyIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(iconSize)
                )
                return@Box
            }

            // Left Icon
            if (leftIcon != null) {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(iconSize)
                )
                Spacer(modifier = Modifier.width(DimensUtils.dimenDp(R.dimen.size_8)))
            }

            // TEXT
            if (text != null) {
                val finalStyle = textStyle.merge(
                    TextStyle(
                        color = contentColor,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Text(
                    text = text,
                    style = finalStyle
                )
            }

            // Right Icon
            if (rightIcon != null) {
                Spacer(modifier = Modifier.width(DimensUtils.dimenDp(R.dimen.size_8)))

                Icon(
                    painter = rightIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }
}