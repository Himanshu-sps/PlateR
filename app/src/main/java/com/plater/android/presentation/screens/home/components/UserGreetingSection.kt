package com.plater.android.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.domain.models.User

/**
 * Composable section that displays a personalized greeting to the user.
 * Shows user's first name, a cooking prompt, and their profile picture (or default icon).
 * Only renders if user data is available.
 *
 * @param modifier Modifier to apply to the section container
 * @param user The user data to display. If null, the section won't render
 */
@Composable
fun UserGreetingSection(
    modifier: Modifier = Modifier,
    user: User?
) {
    if (user != null) {
        Row(
            modifier = modifier.height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(DimensUtils.dimenDp(R.dimen.size_12)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(DimensUtils.dimenDp(R.dimen.size_6))
            ) {
                Text(
                    text = stringResource(R.string.hello_x, "${user?.firstName}"),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = stringResource(R.string.what_are_you_cooking_today),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (!user.image.isNullOrEmpty()) {
                AsyncImage(
                    model = user.image,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1.0f)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_account),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1.0f)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(DimensUtils.dimenDp(R.dimen.size_8)),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}