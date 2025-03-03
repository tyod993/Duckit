package com.company.duckit.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.company.duckit.R
import com.company.duckit.domain.state.LoginScreenState
import com.company.duckit.domain.actions.LoginButtonPressed
import com.company.duckit.domain.actions.LoginEmailInputUpdate
import com.company.duckit.domain.actions.LoginPasswordInputUpdate
import com.company.duckit.domain.actions.LoginScreenAction
import com.company.duckit.domain.actions.LoginSignUpButtonPressed

@Composable
fun LoginScreen(
    state: LoginScreenState,
    submitAction: (LoginScreenAction) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    stringResource(state.title),
                    fontSize = 24.sp
                )

                Spacer(
                    modifier = Modifier.size(height = 24.dp, width = 0.dp)
                )

                if (state.mainErrorText != -1) {
                    Text(
                        stringResource(state.mainErrorText),
                        color = Color.Red
                    )
                }

                TextField(
                    value = state.emailText,
                    onValueChange = { submitAction(LoginEmailInputUpdate(it)) },
                    label = { Text(stringResource(R.string.email)) },
                    isError = state.emailError,
                    supportingText = {
                        if (state.emailError) {
                            Text(
                                stringResource(R.string.email_incorrect),
                                color = Color.Red
                            )
                        }
                    }
                )

                Spacer(
                    modifier = Modifier.size(height = 24.dp, width = 0.dp)
                )

                TextField(
                    value = state.passwordText,
                    onValueChange = { submitAction(LoginPasswordInputUpdate(it)) },
                    label = { Text(stringResource(R.string.password)) },
                    isError = state.passwordError,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    supportingText = {
                        if (state.passwordError) {
                            Text(
                                stringResource(R.string.password_too_short),
                                color = Color.Red
                            )
                        }
                    }
                )

                Spacer(
                    modifier = Modifier.size(height = 24.dp, width = 0.dp)
                )

                Button(
                    onClick = { submitAction(LoginButtonPressed) }
                ) {
                    Text(stringResource(state.buttonText))
                }

                TextButton(
                    onClick = { submitAction(LoginSignUpButtonPressed) }
                ) {
                    Text(stringResource(state.textButtonText))
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview_LoginScreen() {
    LoginScreen(
        state = LoginScreenState(
            title = R.string.login,
            emailText = "",
            passwordText = "",
            buttonText = R.string.login,
            emailError = false,
            passwordError = false,
            isLoading = false,
            textButtonText = R.string.sign_up,
            mainErrorText = -1
        ),
        submitAction = {},
        onDismiss = {}
    )
}

@Preview
@Composable
private fun Preview_LoginScreenErrors() {
    LoginScreen(
        state = LoginScreenState(
            title = R.string.login,
            emailText = "",
            passwordText = "",
            buttonText = R.string.login,
            emailError = true,
            passwordError = true,
            isLoading = false,
            textButtonText = R.string.sign_up,
            mainErrorText = R.string.unknown_error
        ),
        submitAction = {},
        onDismiss = {}
    )
}