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
import domain.card.use_case.FlowCardByRoomAndUserIDUseCase
import domain.card.use_case.SetCardByRoomAndUserIDUseCase
import domain.character.repository.CharacterRepository
import domain.room.repository.BingoRoomRepository
import domain.room.use_case.CreateRoomUseCase
import domain.room.use_case.FlowRoomByIdUseCase
import domain.room.use_case.GetNotStartedRoomsUseCase
import domain.room.use_case.GetRoomByIdUseCase
import domain.room.use_case.GetRoomsUseCase
import domain.room.use_case.GetRunningRoomsUseCase
import domain.room.use_case.JoinRoomUseCase
import domain.room.use_case.RaffleNextCharacterUseCase
import domain.room.use_case.UpdateRoomStateUseCase
import domain.theme.repository.BingoThemeRepository
import domain.theme.use_case.FlowThemeByIdUseCase
import domain.theme.use_case.GetAllThemesUseCase
import domain.theme.use_case.GetCharactersByThemeId
import domain.theme.use_case.GetRoomCharactersUseCase
import domain.theme.use_case.GetRoomThemeUseCase
import domain.user.repository.UserRepository
import domain.user.use_case.FlowUserUseCase
import domain.user.use_case.GetRoomPlayersUseCase
import domain.user.use_case.GetProfilePicturesUseCase
import domain.user.use_case.GetUserByIdUseCase
import domain.user.use_case.UpdateNameUseCase
import domain.user.use_case.UpdateUserPictureUseCase
import domain.user.use_case.UpdateVictoryMessageUseCase
import org.koin.dsl.module

val domainModule = module {

//    Card
    single<CardRepository> { CardRepositoryImpl(get()) }
    factory<FlowCardByRoomAndUserIDUseCase> { FlowCardByRoomAndUserIDUseCase(get()) }
    factory<SetCardByRoomAndUserIDUseCase> { SetCardByRoomAndUserIDUseCase(get()) }

//    Character
    single<CharacterRepository> { CharacterRepositoryImpl(get()) }

//    Room
    single<BingoRoomRepository> { BingoRoomRepositoryImpl(get()) }
    factory<GetRoomsUseCase> { GetRoomsUseCase(get()) }
    factory<GetNotStartedRoomsUseCase> { GetNotStartedRoomsUseCase(get()) }
    factory<GetRunningRoomsUseCase> { GetRunningRoomsUseCase(get()) }
    factory<JoinRoomUseCase> { JoinRoomUseCase(get()) }
    factory<FlowRoomByIdUseCase> { FlowRoomByIdUseCase(get()) }
    factory<GetRoomByIdUseCase> { GetRoomByIdUseCase(get()) }
    factory<UpdateRoomStateUseCase> { UpdateRoomStateUseCase(get()) }
    factory<RaffleNextCharacterUseCase> { RaffleNextCharacterUseCase(get()) }
    factory<CreateRoomUseCase> { CreateRoomUseCase(get()) }

//    Theme
    single<BingoThemeRepository> { BingoThemeRepositoryImpl(get()) }
    single<GetAllThemesUseCase> { GetAllThemesUseCase(get()) }
    single<GetCharactersByThemeId> { GetCharactersByThemeId(get()) }
    factory<FlowThemeByIdUseCase> { FlowThemeByIdUseCase(get()) }
    factory<GetRoomCharactersUseCase> { GetRoomCharactersUseCase(get(), get()) }
    factory<GetRoomThemeUseCase> { GetRoomThemeUseCase(get(), get()) }

//    User
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<GetUserByIdUseCase> { GetUserByIdUseCase(get()) }
    single<FlowUserUseCase> { FlowUserUseCase(get()) }
    single<UpdateNameUseCase> { UpdateNameUseCase(get()) }
    single<UpdateVictoryMessageUseCase> { UpdateVictoryMessageUseCase(get()) }
    single<UpdateUserPictureUseCase> { UpdateUserPictureUseCase(get()) }
    single<GetProfilePicturesUseCase> { GetProfilePicturesUseCase(get(), get()) }
    factory<GetRoomPlayersUseCase> { GetRoomPlayersUseCase(get(), get()) }

//    Auth
    single<AuthService> { AuthServiceImpl(get()) }
    single<AuthenticateUserUseCase> { AuthenticateUserUseCase(get(), get()) }
    single<CreateUserUseCase> { CreateUserUseCase(get(), get()) }
    single<DeleteAccountUseCase> { DeleteAccountUseCase(get()) }
    single<SignOutUseCase> { SignOutUseCase(get()) }
    single<ChangePasswordWithReAuthenticationUseCase> { ChangePasswordWithReAuthenticationUseCase(get()) }
}