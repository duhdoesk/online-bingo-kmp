package data.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module

val dataModule = module {
    single<FirebaseFirestore> { Firebase.firestore }
}