package data.user.repository

import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.user.repository.UserRepository

class UserRepositoryImpl(
    private val firestore: FirebaseFirestore
): UserRepository {
}