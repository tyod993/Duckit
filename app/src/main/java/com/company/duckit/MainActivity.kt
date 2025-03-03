package com.company.duckit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.company.duckit.composables.LoginScreen
import com.company.duckit.composables.MainScreen
import com.company.duckit.controllers.LoginScreenViewModel
import com.company.duckit.controllers.MainScreenViewModel
import com.company.duckit.domain.actions.MainLoginScreenDismissed
import com.company.duckit.ui.theme.DuckItTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber.*
import timber.log.Timber.Forest.plant

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainScreenViewModel: MainScreenViewModel by viewModels()
    private val loginScreenViewModel: LoginScreenViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        plant(DebugTree())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainState by mainScreenViewModel.state.collectAsState()
            val loginState by loginScreenViewModel.state.collectAsState()
            DuckItTheme {
                MainScreen(
                    state = mainState,
                    submitAction = mainScreenViewModel::submitMainScreenAction,
                    loginScreen = {
                        LoginScreen(
                            state = loginState,
                            submitAction = loginScreenViewModel::submitUserAction,
                            onDismiss = {
                                mainScreenViewModel.submitMainScreenAction(
                                    MainLoginScreenDismissed
                                )
                            }
                        )
                    }
                )
            }
        }
    }

}
