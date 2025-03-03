package com.company.duckit.domain.auth.api

import com.company.duckit.domain.auth.UserToken

data class SignInResponse(val token: UserToken?, val error: SignInFailure?)
