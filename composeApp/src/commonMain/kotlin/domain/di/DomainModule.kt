package domain.di

import data.card.repository.CardRepositoryImpl
import data.character.repository.CharacterRepositoryImpl
import data.feature.user.UserRepositoryImpl
import data.room.repository.BingoRoomRepositoryImpl
import data.theme.repository.BingoThemeRepositoryImpl
import domain.billing.SubscribeToUserSubscriptionData
import domain.card.repository.CardRepository
import domain.card.useCase.FlowCardByRoomAndUserIDUseCase
import domain.card.useCase.SetCardByRoomAndUserIDUseCase
import domain.character.repository.CharacterRepository
import domain.character.useCase.GetRoomCharacters
import domain.character.useCase.GetThemeCharacters
import domain.character.useCase.ObserveThemeCharacters
import domain.feature.appVersion.useCase.CheckForUpdatesUseCase
import domain.feature.appVersion.useCase.GetInstalledVersionUseCase
import domain.feature.auth.useCase.GetSessionInfoUseCase
import domain.feature.auth.useCase.GetSessionStatusUseCase
import domain.feature.auth.useCase.SignOutUseCase
import domain.feature.user.UserRepository
import domain.feature.user.useCase.CreateUserUseCase
import domain.feature.user.useCase.DeleteUserUseCase
import domain.feature.user.useCase.GetCurrentUserUseCase
import domain.feature.user.useCase.GetProfilePicturesUseCase
import domain.feature.user.useCase.GetRoomPlayersUseCase
import domain.feature.user.useCase.GetUserByIdUseCase
import domain.feature.user.useCase.UpdateUserNameUseCase
import domain.feature.user.useCase.UpdateUserPictureUseCase
import domain.feature.user.useCase.UpdateUserVictoryMessageUseCase
import domain.room.repository.BingoRoomRepository
import domain.room.useCase.CallBingoUseCase
import domain.room.useCase.CreateRoomUseCase
import domain.room.useCase.FlowRoomByIdUseCase
import domain.room.useCase.GetBingoStyleUseCase
import domain.room.useCase.GetNotStartedRoomsUseCase
import domain.room.useCase.GetRoomByIdUseCase
import domain.room.useCase.GetRoomsUseCase
import domain.room.useCase.GetRunningRoomsUseCase
import domain.room.useCase.JoinRoomUseCase
import domain.room.useCase.MapRoomDTOToModelUseCase
import domain.room.useCase.RaffleNextItemUseCase
import domain.room.useCase.UpdateRoomStateUseCase
import domain.theme.repository.BingoThemeRepository
import domain.theme.useCase.FlowThemeByIdUseCase
import domain.theme.useCase.GetAllThemesUseCase
import domain.theme.useCase.GetRoomThemeUseCase
import domain.theme.useCase.GetThemeByIdUseCase
import domain.theme.useCase.ObserveAvailableThemes
import org.koin.dsl.module

val domainModule = module {

    /** App Version */
    factory { CheckForUpdatesUseCase(get()) }
    factory { GetInstalledVersionUseCase(get()) }

    /** Audio */
    includes(audioModule)

    /** Authentication */
    factory { GetSessionInfoUseCase(get()) }
    factory { GetSessionStatusUseCase(get()) }
    factory { SignOutUseCase(get()) }

    /** Billing */
    single<SubscribeToUserSubscriptionData> { SubscribeToUserSubscriptionData() }

//    Card
    single<CardRepository> { CardRepositoryImpl(get()) }
    single<FlowCardByRoomAndUserIDUseCase> { FlowCardByRoomAndUserIDUseCase(get()) }
    single<SetCardByRoomAndUserIDUseCase> { SetCardByRoomAndUserIDUseCase(get()) }

//    Character
    single<CharacterRepository> { CharacterRepositoryImpl(get()) }

//    Room
    single<BingoRoomRepository> { BingoRoomRepositoryImpl(get()) }
    single { GetRoomsUseCase(get(), get()) }
    single { GetNotStartedRoomsUseCase(get(), get()) }
    single { GetRunningRoomsUseCase(get(), get()) }
    single { JoinRoomUseCase(get()) }
    single { FlowRoomByIdUseCase(get(), get()) }
    single { GetRoomByIdUseCase(get(), get()) }
    single { UpdateRoomStateUseCase(get()) }
    single { RaffleNextItemUseCase(get()) }
    single { CreateRoomUseCase(get()) }
    single { CallBingoUseCase(get()) }
    single { GetBingoStyleUseCase(get(), get()) }
    single { MapRoomDTOToModelUseCase() }

//    Theme
    single<BingoThemeRepository> { BingoThemeRepositoryImpl(get()) }
    single { GetAllThemesUseCase(get()) }
    single { ObserveThemeCharacters(get()) }
    single { FlowThemeByIdUseCase(get()) }
    single { GetRoomCharacters(get(), get()) }
    single { GetRoomThemeUseCase(get(), get()) }
    single { GetThemeCharacters(get()) }
    single { GetThemeByIdUseCase(get()) }
    single { ObserveAvailableThemes(get()) }

//    User
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
    single<GetCurrentUserUseCase> { GetCurrentUserUseCase(get()) }
    single<GetUserByIdUseCase> { GetUserByIdUseCase(get()) }
    single<UpdateUserNameUseCase> { UpdateUserNameUseCase(get()) }
    single<UpdateUserVictoryMessageUseCase> { UpdateUserVictoryMessageUseCase(get()) }
    single<UpdateUserPictureUseCase> { UpdateUserPictureUseCase(get()) }
    single<GetProfilePicturesUseCase> { GetProfilePicturesUseCase(get(), get()) }
    single<GetRoomPlayersUseCase> { GetRoomPlayersUseCase(get(), get()) }
    single<CreateUserUseCase> { CreateUserUseCase(get()) }
    single<DeleteUserUseCase> { DeleteUserUseCase(get()) }
}
