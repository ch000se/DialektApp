package com.example.dialektapp.domain.usecases

import android.util.Patterns
import com.example.dialektapp.domain.util.ValidationError
import com.example.dialektapp.domain.util.ValidationResult
import javax.inject.Inject

class ValidateLoginUseCase @Inject constructor() {

    operator fun invoke(
        username: String,
        password: String,
    ): ValidationResult {
        val errors = mutableListOf<ValidationError>()

        if (username.isBlank()) {
            errors.add(ValidationError.UsernameEmpty)
        } else {
            if (username.length < 3) errors.add(ValidationError.UsernameTooShort)
            if (username.length > 20) errors.add(ValidationError.UsernameTooLong)
            if (!username.matches("^[a-zA-Z0-9_]+$".toRegex())) errors.add(ValidationError.UsernameInvalidChars)
        }

        if (password.isBlank()) {
            errors.add(ValidationError.PasswordEmpty)
        } else {
            if (password.length < 8) errors.add(ValidationError.PasswordTooShort)
            if (password.length > 50) errors.add(ValidationError.PasswordTooLong)
            if (!password.any { it.isUpperCase() }) errors.add(ValidationError.PasswordMissingUpperCase)
            if (!password.any { it.isLowerCase() }) errors.add(ValidationError.PasswordMissingLowerCase)
            if (!password.any { it.isDigit() }) errors.add(ValidationError.PasswordMissingDigit)
            if (!password.any { "!@#$%^&*".contains(it) }) errors.add(ValidationError.PasswordMissingSpecialChar)
        }


        return if (errors.isEmpty()) ValidationResult.Success
        else ValidationResult.Error(errors)
    }
}
