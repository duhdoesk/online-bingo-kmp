package data.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.context.startKoin
import org.koin.dsl.module

val firestoreModule = module {
    single<FirebaseFirestore> { Firebase.firestore }
}

fun dataModule() = listOf(firestoreModule)

fun initKoin() =
    startKoin {
        modules(dataModule())
    }