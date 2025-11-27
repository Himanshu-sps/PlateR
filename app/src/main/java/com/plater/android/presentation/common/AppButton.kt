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

enum class ButtonType {
    Primary,
    Outlined,
    Text,
    IconOnly
}

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
            bgColor = Color.Companion.Transparent
            contentColor = if (enabled) primaryColor else primaryColor.copy(alpha = 0.4f)
            border = BorderStroke(DimensUtils.dimenDp(R.dimen.size_1), contentColor)
        }

        ButtonType.Text -> {
            bgColor = Color.Companion.Transparent
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
                if (border != null) Modifier.Companion.border(
                    border,
                    RoundedCornerShape(percent = cornerRadiusPercent)
                )
                else Modifier.Companion
            )
            .clickable(enabled = enabled) { onClick() }
            .padding(
                horizontal = DimensUtils.dimenDp(R.dimen.size_6),
                vertical = DimensUtils.dimenDp(R.dimen.size_8),
            ),
        contentAlignment = Alignment.Companion.Center
    ) {

        Row(
            verticalAlignment = Alignment.Companion.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            if (type == ButtonType.IconOnly && onlyIcon != null) {
                Icon(
                    imageVector = onlyIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.Companion.size(iconSize)
                )
                return@Box
            }

            // Left Icon
            if (leftIcon != null) {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.Companion.size(iconSize)
                )
                Spacer(modifier = Modifier.width(DimensUtils.dimenDp(R.dimen.size_8)))
            }

            // TEXT
            if (text != null) {
                val finalStyle = textStyle.merge(
                    TextStyle(
                        color = contentColor,
                        fontWeight = FontWeight.Companion.SemiBold
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
                    modifier = Modifier.Companion.size(iconSize)
                )
            }
        }
    }
}