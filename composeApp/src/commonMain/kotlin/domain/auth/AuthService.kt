package domain.auth

import dev.gitlive.firebase.auth.AuthResult
import domain.user.model.User

interface AuthService {

    val currentUser: User?

    suspend fun authenticate(email: String, password: String): AuthResult
    suspend fun createUser(email: String, password: String): AuthResult
    suspend fun sendPasswordResetEmail(email: String)
    suspend fun signOut()
}