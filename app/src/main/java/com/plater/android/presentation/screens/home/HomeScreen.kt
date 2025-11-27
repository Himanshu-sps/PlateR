package com.plater.android.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.presentation.common.AppButton
import com.plater.android.presentation.common.ButtonType

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = DimensUtils.dimenDp(R.dimen.size_16),
                vertical = DimensUtils.dimenDp(R.dimen.size_24)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.home_welcome),
            style = MaterialTheme.typography.headlineSmall
        )

        AppButton(
            modifier = Modifier
                .padding(top = DimensUtils.dimenDp(R.dimen.size_24)),
            text = stringResource(R.string.logout),
            type = ButtonType.Primary,
            onClick = onLogoutClick
        )
    }
}

