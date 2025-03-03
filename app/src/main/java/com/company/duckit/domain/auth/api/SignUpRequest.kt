package com.company.duckit.domain.auth.api

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(val email: String, val password: String)