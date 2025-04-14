package domain.di

import domain.audio.AudioPlayer
import domain.audio.AudioPlayerAndroidImpl
import org.koin.dsl.module

actual val audioModule = module {
    single<AudioPlayer> { AudioPlayerAndroidImpl(get()) }
}
