package com.plater.android.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils

@Composable
fun AppSnackBar(
    snackbarData: SnackbarData,
    snackbarContainerColor: Color = MaterialTheme.colorScheme.errorContainer,
    snackbarContentColor: Color = MaterialTheme.colorScheme.onErrorContainer,
    snackbarTextStyle: TextStyle = MaterialTheme.typography.labelMedium
) {
    Surface(
        shape = RoundedCornerShape(DimensUtils.dimenDp(R.dimen.size_4)),
        color = snackbarContainerColor,
        contentColor = snackbarContentColor,
        tonalElevation = DimensUtils.dimenDp(R.dimen.size_2)
    ) {
        Text(
            text = snackbarData.visuals.message,
            style = snackbarTextStyle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = DimensUtils.dimenDp(R.dimen.size_16),
                    vertical = DimensUtils.dimenDp(R.dimen.size_12)
                ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )
    }
}

