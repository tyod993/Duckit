package com.company.duckit.domain.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserToken(val token: String) {
    fun isLoggedIn(): Boolean = token.isNotEmpty()
}
