package com.bhavya.spendwise.domain.repository

import com.bhavya.spendwise.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val authState: Flow<AuthUser?>

    suspend fun register(
        email: String,
        password: String
    ): Result<AuthUser>

    suspend fun login(
        email: String,
        password: String
    ): Result<AuthUser>

    suspend fun logout(): Result<Unit>

    suspend fun resetPassword(
        email: String
    ): Result<Unit>
}
