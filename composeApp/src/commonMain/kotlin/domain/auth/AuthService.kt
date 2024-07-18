package domain.auth

import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.FirebaseUser

interface AuthService {

    val currentUser: FirebaseUser?

    suspend fun authenticate(email: String, password: String): AuthResult
    suspend fun createUser(email: String, password: String): AuthResult
    suspend fun sendPasswordResetEmail(email: String)
    suspend fun signOut(): Result<Unit>
    suspend fun deleteAccount(): Result<Unit>
    suspend fun updatePassword(newPassword: String)
}