package com.example.dialektapp.domain.util

sealed class ValidationError {

    object UsernameEmpty : ValidationError()
    object UsernameTooShort : ValidationError()
    object UsernameTooLong : ValidationError()
    object UsernameInvalidChars : ValidationError()

    object FullNameEmpty : ValidationError()
    object FullNameTooShort : ValidationError()
    object FullNameTooLong : ValidationError()

    object EmailEmpty : ValidationError()
    object InvalidEmail : ValidationError()
    object EmailAlreadyUsed : ValidationError()

    object PasswordEmpty : ValidationError()
    object PasswordTooShort : ValidationError()
    object PasswordTooLong : ValidationError()
    object PasswordMissingUpperCase : ValidationError()
    object PasswordMissingLowerCase : ValidationError()
    object PasswordMissingDigit : ValidationError()
    object PasswordMissingSpecialChar : ValidationError()

    object PasswordsDoNotMatch : ValidationError()

    object TermsNotAccepted : ValidationError()

}