package com.example.dialektapp.presentation.util

import android.content.Context
import com.example.dialektapp.R
import com.example.dialektapp.domain.util.ValidationError


fun ValidationError.toUserMessage(context: Context): String {
    val resId = when (this) {
        ValidationError.UsernameEmpty -> R.string.username_empty
        ValidationError.UsernameTooShort -> R.string.username_too_short
        ValidationError.UsernameTooLong -> R.string.username_too_long
        ValidationError.UsernameInvalidChars -> R.string.username_invalid_chars

        ValidationError.FullNameEmpty -> R.string.fullname_empty
        ValidationError.FullNameTooShort -> R.string.fullname_too_short
        ValidationError.FullNameTooLong -> R.string.fullname_too_long

        ValidationError.EmailEmpty -> R.string.email_empty
        ValidationError.InvalidEmail -> R.string.email_invalid
        ValidationError.EmailAlreadyUsed -> R.string.email_already_used

        ValidationError.PasswordEmpty -> R.string.password_empty
        ValidationError.PasswordTooShort -> R.string.password_too_short
        ValidationError.PasswordTooLong -> R.string.password_too_long
        ValidationError.PasswordMissingUpperCase -> R.string.password_miss_upper_case
        ValidationError.PasswordMissingLowerCase -> R.string.password_miss_lower_case
        ValidationError.PasswordMissingDigit -> R.string.password_miss_digit
        ValidationError.PasswordMissingSpecialChar -> R.string.password_miss_special_char

        ValidationError.PasswordsDoNotMatch -> R.string.password_dont_match
        ValidationError.TermsNotAccepted -> R.string.terms_not_accepted
    }
    return context.getString(resId)
}

