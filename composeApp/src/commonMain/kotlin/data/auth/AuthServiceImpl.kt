package data.auth

import dev.gitlive.firebase.auth.AuthCredential
import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.EmailAuthProvider
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import domain.auth.AuthService

class AuthServiceImpl(
    private val auth: FirebaseAuth,
) : AuthService {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun authenticate(email: String, password: String): AuthResult {
        return auth.signInWithEmailAndPassword(email, password)
    }

    override suspend fun createUser(email: String, password: String): AuthResult {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email = email)
    }

    override suspend fun signOut(): Result<Unit> {
        try {
            if (auth.currentUser?.isAnonymous == true) auth.currentUser?.delete()
            auth.signOut()

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun deleteAccount(): Result<Unit> {
        try {
            auth.currentUser?.delete()
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun updatePassword(newPassword: String): Result<Unit> {
        try {
            auth.currentUser?.updatePassword(newPassword)
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun reAuthenticateUser(credential: AuthCredential): Result<Unit> {
        try {
            auth.currentUser?.reauthenticate(credential = credential)
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}