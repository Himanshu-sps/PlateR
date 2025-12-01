package com.plater.android.presentation.screens.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier
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
            text = "Bookmark",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

