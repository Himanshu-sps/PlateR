package com.plater.android.presentation.screens.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.presentation.common.AppButton
import com.plater.android.presentation.common.ButtonType
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel()
) {
    val userState by viewModel.userState.collectAsState()
    val user = userState?.user
    val currentUser by viewModel.currentUser.collectAsState()
    val isLoadingUser by viewModel.isLoadingUser.collectAsState()

    // Snackbar setup for showing error messages
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Observe viewEffects and show snackbar when error occurs
    LaunchedEffect(Unit) {
        viewModel.viewEffects.collect { errorMessage ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = errorMessage,
                    duration = androidx.compose.material3.SnackbarDuration.Short
                )
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = DimensUtils.dimenDp(R.dimen.size_16),
                    vertical = DimensUtils.dimenDp(R.dimen.size_24)
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Account",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_24)))

            // Use currentUser if available, otherwise fallback to userState
            val displayUser = currentUser ?: user

            if (displayUser != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(DimensUtils.dimenDp(R.dimen.size_16)),
                    verticalArrangement = Arrangement.spacedBy(DimensUtils.dimenDp(R.dimen.size_16))
                ) {
                    // Profile Picture and Name Section
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(DimensUtils.dimenDp(R.dimen.size_16))
                    ) {
                        if (!displayUser.image.isNullOrEmpty()) {
                            AsyncImage(
                                model = displayUser.image,
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                            )
                        } else {
                            androidx.compose.material3.Icon(
                                painter = androidx.compose.ui.res.painterResource(id = R.drawable.ic_account),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .padding(DimensUtils.dimenDp(R.dimen.size_20)),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        Column {
                            val fullName = buildString {
                                if (!displayUser.firstName.isNullOrEmpty()) append(displayUser.firstName)
                                if (!displayUser.lastName.isNullOrEmpty()) {
                                    if (isNotEmpty()) append(" ")
                                    append(displayUser.lastName)
                                }
                                if (isEmpty() && !displayUser.username.isNullOrEmpty()) {
                                    append(displayUser.username)
                                }
                            }

                            if (fullName.isNotEmpty()) {
                                Text(
                                    text = fullName,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            if (!displayUser.username.isNullOrEmpty() && displayUser.firstName?.isNotEmpty() == true) {
                                Text(
                                    text = "@${displayUser.username}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Divider(color = MaterialTheme.colorScheme.outlineVariant)

                    // User Information Details
                    Column(
                        verticalArrangement = Arrangement.spacedBy(DimensUtils.dimenDp(R.dimen.size_12))
                    ) {
                        if (!displayUser.email.isNullOrEmpty()) {
                            UserInfoRow(
                                label = stringResource(R.string.email),
                                value = displayUser.email
                            )
                        }

                        if (!displayUser.username.isNullOrEmpty()) {
                            UserInfoRow(
                                label = stringResource(R.string.username),
                                value = displayUser.username
                            )
                        }

                        if (!displayUser.firstName.isNullOrEmpty()) {
                            UserInfoRow(
                                label = "First Name",
                                value = displayUser.firstName
                            )
                        }

                        if (!displayUser.lastName.isNullOrEmpty()) {
                            UserInfoRow(
                                label = "Last Name",
                                value = displayUser.lastName
                            )
                        }

                        if (!displayUser.gender.isNullOrEmpty()) {
                            UserInfoRow(
                                label = "Gender",
                                value = displayUser.gender
                            )
                        }

                        if (displayUser.id != null) {
                            UserInfoRow(
                                label = "User ID",
                                value = displayUser.id.toString()
                            )
                        }
                    }

                    Divider(color = MaterialTheme.colorScheme.outlineVariant)

                    // Action Buttons
                    Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_8)))
                    AppButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (isLoadingUser) "Refreshing..." else "Refresh User Info",
                        type = ButtonType.Outlined,
                        onClick = { viewModel.fetchCurrentUser() },
                        enabled = !isLoadingUser
                    )

                    Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_12)))
                    AppButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Logout",
                        type = ButtonType.Outlined,
                        onClick = { viewModel.logout() }
                    )
                }
            }
        } else {
            Text(
                text = "No user information available",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        }
    }
}

@Composable
private fun UserInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}

