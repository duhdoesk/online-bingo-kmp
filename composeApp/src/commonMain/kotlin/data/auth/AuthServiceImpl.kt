package data.auth

import dev.gitlive.firebase.auth.AuthResult
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

    override suspend fun signOut() {
        if (auth.currentUser?.isAnonymous == true) auth.currentUser?.delete()
        auth.signOut()
    }
}