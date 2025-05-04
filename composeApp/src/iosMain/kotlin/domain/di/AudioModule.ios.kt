package domain.di

import domain.audio.AudioPlayer
import domain.audio.AudioPlayerIosImpl
import org.koin.dsl.module

actual val audioModule = module {
    single<AudioPlayer> { AudioPlayerIosImpl() }
}
