package com.company.duckit.controllers

import com.company.duckit.domain.auth.UserToken
import kotlinx.coroutines.flow.MutableStateFlow

class UserTokenProvider {
    val userTokenFlow: MutableStateFlow<UserToken> = MutableStateFlow(UserToken(""))
    fun setUserToken(token: UserToken) {
        userTokenFlow.tryEmit(token)
    }
}