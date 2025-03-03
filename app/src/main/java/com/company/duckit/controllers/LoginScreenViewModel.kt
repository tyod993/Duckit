package com.company.duckit.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.duckit.R
import com.company.duckit.domain.actions.LoginButtonPressed
import com.company.duckit.domain.actions.LoginEmailInputUpdate
import com.company.duckit.domain.actions.LoginPasswordInputUpdate
import com.company.duckit.domain.actions.LoginScreenAction
import com.company.duckit.domain.state.LoginScreenState
import com.company.duckit.domain.actions.LoginSignUpButtonPressed
import com.company.duckit.domain.auth.api.SignInFailure
import com.company.duckit.domain.auth.api.SignUpFailure
import com.company.duckit.domain.isValidEmail
import com.company.duckit.interactors.UserInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val userTokenProvider: UserTokenProvider
) : ViewModel() {

    val state = MutableStateFlow(
        LoginScreenState(
            title = R.string.login,
            emailText = "",
            passwordText = "",
            buttonText = R.string.login,
            emailError = false,
            passwordError = false,
            isLoading = false,
            textButtonText = R.string.sign_up,
            mainErrorText = -1,
        )
    )

    fun submitUserAction(action: LoginScreenAction) {
        when (action) {
            is LoginButtonPressed -> handleLoginButtonPressed()
            is LoginEmailInputUpdate -> updateEmailText(action.newValue)
            is LoginPasswordInputUpdate -> updatePasswordText(action.newValue)
            is LoginSignUpButtonPressed -> switchToOppositeAction()
        }
    }

    private fun handleLoginButtonPressed() {
        state.update { state.value.copy(passwordError = false, emailError = false) }
        val valid = validateInputs()
        if (!valid) return

        state.update { state.value.copy(isLoading = true) }

        if (state.value.buttonText == R.string.login) {
            viewModelScope.launch(Dispatchers.IO) {
                userInteractor.signIn(state.value.emailText, state.value.passwordText)
                    .collect { response ->
                        state.update { state.value.copy(isLoading = false) }
                        if (response.token != null) {
                            userTokenProvider.setUserToken(response.token)
                            return@collect
                        }
                        if (response.error != null) {

                            state.update {
                                state.value.copy(
                                    mainErrorText = response.error.getText(),
                                    isLoading = false
                                )
                            }
                            return@collect
                        }
                    }
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                userInteractor.signUp(state.value.emailText, state.value.passwordText)
                    .collect { response ->
                        state.update { state.value.copy(isLoading = false) }
                        if (response.token != null) {
                            userTokenProvider.setUserToken(response.token)
                            return@collect
                        }
                        if (response.error != null) {
                            state.update {
                                state.value.copy(
                                    mainErrorText = response.error.getText(),
                                    isLoading = false
                                )
                            }
                            return@collect
                        }
                    }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val isEmailValid = state.value.emailText.isValidEmail()
        val isPasswordValid = state.value.passwordText.length > 3

        if (!isEmailValid) {
            state.update { state.value.copy(emailError = true) }
        }
        if (!isPasswordValid) {
            state.update { state.value.copy(passwordError = true) }
        }

        return isEmailValid && isPasswordValid
    }

    private fun updateEmailText(newValue: String) {
        state.update { state.value.copy(emailText = newValue) }
    }

    private fun updatePasswordText(newValue: String) {
        state.update { state.value.copy(passwordText = newValue) }
    }

    private fun switchToOppositeAction() {
        if (state.value.textButtonText == R.string.sign_up) {
            state.update {
                state.value.copy(
                    title = R.string.sign_up,
                    buttonText = R.string.sign_up,
                    textButtonText = R.string.login
                )
            }
        } else {
            state.update {
                state.value.copy(
                    title = R.string.login,
                    buttonText = R.string.login,
                    textButtonText = R.string.sign_up
                )
            }
        }
    }

    private fun SignInFailure.getText(): Int {
        return when (this) {
            SignInFailure.INCORRECT_PW -> R.string.incorrect_pw
            SignInFailure.ACCOUNT_NOT_FOUND -> R.string.account_not_found
            SignInFailure.UNKNOWN -> R.string.unknown_error
        }
    }

    private fun SignUpFailure.getText(): Int {
        return when (this) {
            SignUpFailure.ACCOUNT_EXISTS -> R.string.account_exists
            SignUpFailure.UNKNOWN -> R.string.unknown_error
        }
    }
}