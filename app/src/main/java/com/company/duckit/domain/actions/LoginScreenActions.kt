package com.company.duckit.domain.actions

sealed interface LoginScreenAction
data class LoginEmailInputUpdate(val newValue: String) : LoginScreenAction
data class LoginPasswordInputUpdate(val newValue: String) : LoginScreenAction
data object LoginButtonPressed : LoginScreenAction
data object LoginSignUpButtonPressed : LoginScreenAction
