package com.company.duckit.domain.auth.api

import com.company.duckit.domain.auth.UserToken

data class SignUpResponse(val token: UserToken?, val error: SignUpFailure?)
