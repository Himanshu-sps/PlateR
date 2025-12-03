package com.plater.android.core.utils

import android.util.Patterns
import androidx.annotation.StringRes
import com.plater.android.R

/**
 * Utility object for validating user input fields.
 * Provides validation methods for email, username, and password fields.
 */
object ValidationUtils {

    /**
     * Data class representing the result of a validation operation.
     *
     * @property isValid Whether the input is valid
     * @property errorResId Optional string resource ID for error message if validation fails
     */
    data class ValidationResult(
        val isValid: Boolean,
        @StringRes val errorResId: Int? = null
    )

    /**
     * Validates an email address.
     * Checks if the email is not empty and matches a valid email pattern.
     *
     * @param input The email string to validate
     * @return [ValidationResult] indicating if the email is valid and any error message
     */
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

    /**
     * Validates a username.
     * Checks if the username is not empty.
     *
     * @param input The username string to validate
     * @return [ValidationResult] indicating if the username is valid and any error message
     */
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

    /**
     * Validates a password.
     * Checks if the password is not empty.
     *
     * @param input The password string to validate
     * @return [ValidationResult] indicating if the password is valid and any error message
     */
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

