package com.plater.android.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.presentation.common.AppButton
import com.plater.android.presentation.common.AppSnackBar
import com.plater.android.presentation.common.AppTextField
import com.plater.android.presentation.common.ButtonType

/**
 * High-level login surface. Collects user input, renders validation feedback,
 * and delegates business logic to [AuthViewModel].
 */
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    onSignInSuccess: () -> Unit
) {
    val uiState by viewModel.authState.collectAsState()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.viewEffects.collect { effect ->
            when (effect) {
                is AuthSharedFlowEffect.NavigateHome -> onSignInSuccess()
                is AuthSharedFlowEffect.ShowValidationErrors -> {
                    usernameError = effect.usernameErrorResId?.let { context.getString(it) }
                    passwordError = effect.passwordErrorResId?.let { context.getString(it) }
                }

                is AuthSharedFlowEffect.ShowError -> {
                    snackbarHostState.showSnackbar(message = effect.message)
                }
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = DimensUtils.dimenDp(R.dimen.size_16),
                        topEnd = DimensUtils.dimenDp(R.dimen.size_16)
                    )
                )
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                .padding(
                    horizontal = DimensUtils.dimenDp(R.dimen.size_12),
                    vertical = DimensUtils.dimenDp(R.dimen.size_24)
                )
                .consumeWindowInsets(WindowInsets(0, 0, 0, 0)),
            verticalArrangement = Arrangement.Top
        ) {
            // Greeting Section
        Text(
            text = stringResource(R.string.hello) + ",",
            style = MaterialTheme.typography.titleLarge
        )
            Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_4)))
        Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.bodyMedium
        )

            Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_32)))

            // Username Field
            AppTextField(
                value = username,
                onValueChange = {
                    username = it
                    if (usernameError != null && it.isNotBlank()) {
                        usernameError = null
                    }
                },
                label = stringResource(R.string.username),
                placeholder = stringResource(R.string.enter_username),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = usernameError != null,
                errorText = usernameError
            )

            Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_16)))

            // Password Field
            AppTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (passwordError != null && it.isNotBlank()) {
                        passwordError = null
                    }
                },
                label = stringResource(R.string.enter_password),
                placeholder = stringResource(R.string.enter_password),
                isPassword = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = passwordError != null,
                errorText = passwordError
            )

            Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_24)))

            // Sign In Button
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.sign_in),
                type = ButtonType.Primary,
                rightIcon = painterResource(R.drawable.ic_arrow_right),
                primaryColor = MaterialTheme.colorScheme.primary,
                enabled = !uiState.isLoading,
                onClick = {
                    usernameError = null
                    passwordError = null
                    viewModel.onEvent(
                        AuthUIEvents.RequestUserLogin(
                            username = username.trim(),
                            password = password.trim()
                        )
                    )
                }
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    horizontal = DimensUtils.dimenDp(R.dimen.size_12),
                    vertical = DimensUtils.dimenDp(R.dimen.size_16)
                ),
            snackbar = { snackbarData ->
                AppSnackBar(snackbarData = snackbarData)
            }
        )
    }
}