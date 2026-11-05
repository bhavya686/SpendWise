package com.bhavya.spendwise.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    val authState: Flow<FirebaseUser?> = callbackFlow {

        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser)
        }

        firebaseAuth.addAuthStateListener(authStateListener)

        awaitClose {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    suspend fun register(
        email: String,
        password: String
    ): FirebaseUser {
        val result = firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .await()

        return requireNotNull(result.user) {
            "Firebase did not return a user after registration."
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): FirebaseUser {
        val result = firebaseAuth
            .signInWithEmailAndPassword(email, password)
            .await()

        return requireNotNull(result.user) {
            "Firebase did not return a user after login."
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    suspend fun resetPassword(email: String) {
        firebaseAuth
            .sendPasswordResetEmail(email)
            .await()
    }
}
