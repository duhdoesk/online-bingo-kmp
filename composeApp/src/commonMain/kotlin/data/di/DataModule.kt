package data.di

import data.dispatcher.DispatcherProvider
import data.feature.appVersion.AppVersionRepositoryImpl
import data.feature.auth.AuthRepositoryImpl
import data.supabase.createSupabaseAdminClient
import data.supabase.createThemeBingoSupabaseClient
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import domain.feature.appVersion.AppVersionRepository
import domain.feature.auth.AuthRepository
import io.github.jan.supabase.SupabaseClient
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val dataModule = module {
    /** Dispatcher Provider */
    single { DispatcherProvider() }

    /** Firebase */
    single<FirebaseFirestore> { Firebase.firestore }

    /** Supabase */
    single<SupabaseClient> { createThemeBingoSupabaseClient() }
    single<SupabaseClient>(qualifier = qualifier("admin")) { createSupabaseAdminClient() }

    /** App Version */
    factory<AppVersionRepository> { AppVersionRepositoryImpl(get()) }

    /** Authentication */
    factory<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}
