package com.example.dialektapp.domain.usecases.validation

import android.util.Patterns
import com.example.dialektapp.domain.util.ValidationError
import com.example.dialektapp.domain.util.ValidationResult
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    operator fun invoke(email: String): ValidationResult {
        val errors = mutableListOf<ValidationError>()

        if (email.isBlank()) {
            errors.add(ValidationError.EmailEmpty)
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                errors.add(ValidationError.InvalidEmail)
            }
        }

        return if (errors.isEmpty()) ValidationResult.Success
        else ValidationResult.Error(errors)
    }
}
