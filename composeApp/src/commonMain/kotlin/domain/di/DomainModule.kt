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
import domain.auth.use_case.ChangePasswordWithReAuthenticationUseCase
import domain.card.repository.CardRepository
import domain.character.repository.CharacterRepository
import domain.room.repository.BingoRoomRepository
import domain.room.use_case.GetNotStartedRoomsUseCase
import domain.room.use_case.GetRoomsUseCase
import domain.room.use_case.GetRunningRoomsUseCase
import domain.room.use_case.JoinRoomUseCase
import domain.theme.repository.BingoThemeRepository
import domain.theme.use_case.GetAllThemes
import domain.theme.use_case.GetCharactersByThemeId
import domain.user.repository.UserRepository
import domain.user.use_case.FlowUserUseCase
import domain.user.use_case.GetProfilePicturesUseCase
import domain.user.use_case.GetUserByIdUseCase
import domain.user.use_case.UpdateNameUseCase
import domain.user.use_case.UpdateUserPictureUseCase
import domain.user.use_case.UpdateVictoryMessageUseCase
import org.koin.dsl.module

val domainModule = module {

//    Card
    single<CardRepository> { CardRepositoryImpl(get()) }

//    Character
    single<CharacterRepository> { CharacterRepositoryImpl(get()) }

//    Room
    single<BingoRoomRepository> { BingoRoomRepositoryImpl(get()) }
    factory<GetRoomsUseCase> { GetRoomsUseCase(get()) }
    factory<GetNotStartedRoomsUseCase> { GetNotStartedRoomsUseCase(get()) }
    factory<GetRunningRoomsUseCase> { GetRunningRoomsUseCase(get()) }
    factory<JoinRoomUseCase> { JoinRoomUseCase(get()) }

//    Theme
    single<BingoThemeRepository> { BingoThemeRepositoryImpl(get()) }
    single<GetAllThemes> { GetAllThemes(get()) }
    single<GetCharactersByThemeId> { GetCharactersByThemeId(get()) }

//    User
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<GetUserByIdUseCase> { GetUserByIdUseCase(get()) }
    single<FlowUserUseCase> { FlowUserUseCase(get()) }
    single<UpdateNameUseCase> { UpdateNameUseCase(get()) }
    single<UpdateVictoryMessageUseCase> { UpdateVictoryMessageUseCase(get()) }
    single<UpdateUserPictureUseCase> { UpdateUserPictureUseCase(get()) }
    single<GetProfilePicturesUseCase> { GetProfilePicturesUseCase(get(), get()) }

//    Auth
    single<AuthService> { AuthServiceImpl(get()) }
    single<AuthenticateUserUseCase> { AuthenticateUserUseCase(get(), get()) }
    single<CreateUserUseCase> { CreateUserUseCase(get(), get()) }
    single<DeleteAccountUseCase> { DeleteAccountUseCase(get()) }
    single<SignOutUseCase> { SignOutUseCase(get()) }
    single<ChangePasswordWithReAuthenticationUseCase> { ChangePasswordWithReAuthenticationUseCase(get()) }
}