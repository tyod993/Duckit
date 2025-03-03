package com.company.duckit.domain.state

data class LoginScreenState(
    val title: Int,
    val emailText: String,
    val passwordText: String,
    val buttonText: Int,
    val emailError: Boolean,
    val passwordError: Boolean,
    val mainErrorText: Int,
    val isLoading: Boolean,
    val textButtonText: Int,
    val isVisable: Boolean = true
)
