package com.company.duckit.domain.interfaces

import com.company.duckit.domain.auth.api.SignInResponse
import com.company.duckit.domain.auth.api.SignUpResponse

interface IUserService {

    suspend fun signIn(email: String, password: String): SignInResponse
    suspend fun signUp(email: String, password: String): SignUpResponse
}