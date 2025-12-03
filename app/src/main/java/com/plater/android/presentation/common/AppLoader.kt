package com.plater.android.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.presentation.uiresources.NeutralBlack

/**
 * Reusable loading indicator component that displays a centered loading dialog.
 * Shows a chef cap icon and loading text with a semi-transparent overlay background.
 *
 * @param modifier Modifier to apply to the loader container
 * @param loadingText Text to display below the loading icon. Defaults to "Loading"
 */
@Composable
fun AppLoader(
    modifier: Modifier = Modifier,
    loadingText: String = stringResource(R.string.loading)
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NeutralBlack.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(DimensUtils.dimenDp(R.dimen.size_16))
                )
                .padding(
                    vertical = DimensUtils.dimenDp(R.dimen.size_18),
                    horizontal = DimensUtils.dimenDp(R.dimen.size_20)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chef_cap),
                contentDescription = "Loading",
                modifier = Modifier.size(DimensUtils.dimenDp(R.dimen.size_48)),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_16)))

            Text(
                text = loadingText,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}

