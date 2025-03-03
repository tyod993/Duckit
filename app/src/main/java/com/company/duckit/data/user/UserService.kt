package com.company.duckit.data.user

import com.company.duckit.domain.auth.api.SignInFailure
import com.company.duckit.domain.auth.api.SignInRequest
import com.company.duckit.domain.auth.api.SignInResponse
import com.company.duckit.domain.auth.api.SignUpFailure
import com.company.duckit.domain.auth.api.SignUpRequest
import com.company.duckit.domain.auth.api.SignUpResponse
import com.company.duckit.domain.interfaces.IUserService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserService(private var client: HttpClient) : IUserService {

    override suspend fun signIn(email: String, password: String): SignInResponse {
        val response = client.post("/signin") {
            contentType(ContentType.Application.Json)
            setBody(SignInRequest(email, password))
        }

        return when (response.status.value) {
            200 -> SignInResponse(token = response.body(), error = null)
            403 -> SignInResponse(token = null, error = SignInFailure.INCORRECT_PW)
            404 -> SignInResponse(token = null, error = SignInFailure.ACCOUNT_NOT_FOUND)
            else -> SignInResponse(token = null, error = SignInFailure.UNKNOWN)
        }

    }

    override suspend fun signUp(email: String, password: String): SignUpResponse {
        val response = client.post("/signup") {
            contentType(ContentType.Application.Json)
            setBody(SignUpRequest(email, password))
        }

        return when (response.status.value) {
            200 -> SignUpResponse(token = response.body(), error = null)
            409 -> SignUpResponse(token = null, error = SignUpFailure.ACCOUNT_EXISTS)
            else -> SignUpResponse(token = null, error = SignUpFailure.UNKNOWN)
        }
    }
}