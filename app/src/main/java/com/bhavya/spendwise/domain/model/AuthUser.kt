package com.bhavya.spendwise.domain.model

data class AuthUser(
    val uid: String,
    val email: String?,
    val displayName: String? = null,
    val isEmailVerified: Boolean = false
)
