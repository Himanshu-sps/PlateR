package com.plater.android.core.utils

import android.util.Patterns
import androidx.annotation.StringRes
import com.plater.android.R

object ValidationUtils {

    data class ValidationResult(
        val isValid: Boolean,
        @StringRes val errorResId: Int? = null
    )

    fun validateEmail(input: String): ValidationResult {
        val email = input.trim()
        return when {
            email.isEmpty() -> ValidationResult(
                isValid = false,
                errorResId = R.string.error_email_required
            )

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult(
                isValid = false,
                errorResId = R.string.error_email_invalid
            )

            else -> ValidationResult(isValid = true)
        }
    }

    fun validateUsername(input: String): ValidationResult {
        val username = input.trim()
        return if (username.isEmpty()) {
            ValidationResult(
                isValid = false,
                errorResId = R.string.error_username_required
            )
        } else {
            ValidationResult(isValid = true)
        }
    }

    fun validatePassword(input: String): ValidationResult {
        val password = input.trim()
        return if (password.isEmpty()) {
            ValidationResult(
                isValid = false,
                errorResId = R.string.error_password_required
            )
        } else {
            ValidationResult(isValid = true)
        }
    }
}

