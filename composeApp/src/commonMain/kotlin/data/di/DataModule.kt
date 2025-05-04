package data.di

import data.dispatcher.DispatcherProvider
import data.feature.auth.AuthRepositoryImpl
import data.supabase.createThemeBingoSupabaseClient
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import domain.feature.auth.AuthRepository
import io.github.jan.supabase.SupabaseClient
import org.koin.dsl.module

val dataModule = module {
    /** Dispatcher Provider */
    single { DispatcherProvider() }

    /** Firebase */
    single<FirebaseFirestore> { Firebase.firestore }

    /** Supabase */
    single<SupabaseClient> { createThemeBingoSupabaseClient() }

    /** Authentication */
    factory<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}
