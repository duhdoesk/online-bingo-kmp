package domain.di

import data.card.repository.CardRepositoryImpl
import data.character.repository.CharacterRepositoryImpl
import data.room.repository.BingoRoomRepositoryImpl
import data.theme.repository.BingoThemeRepositoryImpl
import data.user.repository.UserRepositoryImpl
import domain.card.repository.CardRepository
import domain.character.repository.CharacterRepository
import domain.room.repository.BingoRoomRepository
import domain.theme.repository.BingoThemeRepository
import domain.user.repository.UserRepository
import org.koin.dsl.module

val domainModule = module {
    single<CardRepository> { CardRepositoryImpl(get()) }
    single<CharacterRepository> { CharacterRepositoryImpl(get()) }
    single<BingoRoomRepository> { BingoRoomRepositoryImpl(get()) }
    single<BingoThemeRepository> { BingoThemeRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}