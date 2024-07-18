package domain.di

import data.auth.AuthServiceImpl
import data.card.repository.CardRepositoryImpl
import data.character.repository.CharacterRepositoryImpl
import data.room.repository.BingoRoomRepositoryImpl
import data.theme.repository.BingoThemeRepositoryImpl
import data.user.repository.UserRepositoryImpl
import domain.auth.AuthService
import domain.auth.use_case.AuthenticateUserUseCase
import domain.auth.use_case.CreateUserUseCase
import domain.auth.use_case.DeleteAccountUseCase
import domain.auth.use_case.SignOutUseCase
import domain.auth.use_case.UpdatePasswordUseCase
import domain.card.repository.CardRepository
import domain.character.repository.CharacterRepository
import domain.room.repository.BingoRoomRepository
import domain.theme.repository.BingoThemeRepository
import domain.user.repository.UserRepository
import domain.user.use_case.GetUserByIdUseCase
import domain.user.use_case.UpdateNameUseCase
import domain.user.use_case.UpdateVictoryMessageUseCase
import org.koin.dsl.module

val domainModule = module {

//    Card
    single<CardRepository> { CardRepositoryImpl(get()) }

//    Character
    single<CharacterRepository> { CharacterRepositoryImpl(get()) }

//    Room
    single<BingoRoomRepository> { BingoRoomRepositoryImpl(get()) }

//    Theme
    single<BingoThemeRepository> { BingoThemeRepositoryImpl(get()) }

//    User
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<GetUserByIdUseCase> { GetUserByIdUseCase(get()) }
    single<UpdateNameUseCase> { UpdateNameUseCase(get()) }
    single<UpdateVictoryMessageUseCase> { UpdateVictoryMessageUseCase(get()) }

//    Auth
    single<AuthService> { AuthServiceImpl(get()) }
    single<AuthenticateUserUseCase> { AuthenticateUserUseCase(get(), get()) }
    single<CreateUserUseCase> { CreateUserUseCase(get(), get()) }
    single<DeleteAccountUseCase> { DeleteAccountUseCase(get()) }
    single<SignOutUseCase> { SignOutUseCase(get()) }
    single<UpdatePasswordUseCase> { UpdatePasswordUseCase(get()) }
}