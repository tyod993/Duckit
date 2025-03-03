package com.company.duckit.interactors

import com.company.duckit.domain.auth.api.SignInResponse
import com.company.duckit.domain.auth.api.SignUpResponse
import com.company.duckit.domain.interfaces.IUserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserInteractor(private val service: IUserService) {

    fun signIn(email: String, password: String): Flow<SignInResponse> = flow {
        val res = service.signIn(email, password)
        emit(res)
    }

    fun signUp(email: String, password: String): Flow<SignUpResponse> = flow {
        val res = service.signUp(email, password)
        emit(res)
    }
}