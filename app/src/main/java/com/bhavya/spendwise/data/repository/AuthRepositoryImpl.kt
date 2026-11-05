package com.bhavya.spendwise.data.repository

import com.bhavya.spendwise.data.remote.FirebaseAuthDataSource
import com.bhavya.spendwise.domain.model.AuthUser
import com.bhavya.spendwise.domain.repository.AuthRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: FirebaseAuthDataSource
) : AuthRepository {

    override val authState: Flow<AuthUser?> =
        authDataSource.authState.map { firebaseUser ->
            firebaseUser?.let { user ->
                AuthUser(
                    uid = user.uid,
                    email = user.email,
                    displayName = user.displayName,
                    isEmailVerified = user.isEmailVerified
                )
            }
        }

    override suspend fun register(
        email: String,
        password: String
    ): Result<AuthUser> = executeAuthOperation {
        authDataSource.register(email, password).toAuthUser()
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthUser> = executeAuthOperation {
        authDataSource.login(email, password).toAuthUser()
    }

    override suspend fun logout(): Result<Unit> = executeAuthOperation {
        authDataSource.logout()
    }

    override suspend fun resetPassword(
        email: String
    ): Result<Unit> = executeAuthOperation {
        authDataSource.resetPassword(email)
    }

    private suspend fun <T> executeAuthOperation(
        operation: suspend () -> T
    ): Result<T> {
        return try {
            Result.success(operation())
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    private fun com.google.firebase.auth.FirebaseUser.toAuthUser(): AuthUser {
        return AuthUser(
            uid = uid,
            email = email,
            displayName = displayName,
            isEmailVerified = isEmailVerified
        )
    }
}
