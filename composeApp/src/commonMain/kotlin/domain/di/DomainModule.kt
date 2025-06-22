package domain.di

import data.card.repository.CardRepositoryImpl
import data.character.repository.CharacterRepositoryImpl
import data.feature.user.UserRepositoryImplFirestore
import data.profilePictures.ProfilePicturesRepositoryImpl
import data.room.repository.BingoRoomRepositoryImpl
import data.theme.repository.ThemeRepositoryImpl
import domain.billing.SubscribeToUserSubscriptionData
import domain.card.repository.CardRepository
import domain.card.useCase.FlowCardByRoomAndUserIDUseCase
import domain.card.useCase.SetCardByRoomAndUserIDUseCase
import domain.character.repository.CharacterRepository
import domain.character.useCase.GetThemeCharacters
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
import domain.profilePictures.ProfilePicturesRepository
import domain.room.repository.BingoRoomRepository
import domain.room.useCase.CallBingoUseCase
import domain.room.useCase.CreateRoomUseCase
import domain.room.useCase.GetAvailableRoomsUseCase
import domain.room.useCase.GetOpenRoomsUseCase
import domain.room.useCase.GetRoomByIdUseCase
import domain.room.useCase.GetRunningRoomsUseCase
import domain.room.useCase.JoinRoomUseCase
import domain.room.useCase.RaffleNextItemUseCase
import domain.room.useCase.UpdateRoomStateUseCase
import domain.theme.repository.ThemeRepository
import domain.theme.useCase.GetAllThemesUseCase
import domain.theme.useCase.GetAvailableThemes
import domain.theme.useCase.GetThemeByIdUseCase
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
    factory { FlowCardByRoomAndUserIDUseCase(get()) }
    factory { SetCardByRoomAndUserIDUseCase(get()) }

//    Character
    single<CharacterRepository> { CharacterRepositoryImpl(get()) }

//    Profile Pictures
    single<ProfilePicturesRepository> { ProfilePicturesRepositoryImpl(get(), get()) }
    factory { GetProfilePicturesUseCase(get()) }

//    Room
    single<BingoRoomRepository> { BingoRoomRepositoryImpl(get(), get(), get(), get()) }
    factory { GetOpenRoomsUseCase(get()) }
    factory { GetAvailableRoomsUseCase(get()) }
    factory { GetRunningRoomsUseCase(get()) }
    factory { JoinRoomUseCase(get()) }
    factory { GetRoomByIdUseCase(get()) }
    factory { UpdateRoomStateUseCase(get()) }
    factory { RaffleNextItemUseCase(get()) }
    factory { CreateRoomUseCase(get()) }
    factory { CallBingoUseCase(get()) }

//    Theme
    single<ThemeRepository> { ThemeRepositoryImpl(get()) }
    factory { GetAllThemesUseCase(get()) }
    factory { GetThemeCharacters(get()) }
    factory { GetThemeByIdUseCase(get()) }
    factory { GetAvailableThemes(get()) }

//    User
    single<UserRepository> { UserRepositoryImplFirestore(get(), get(), get()) }
    factory { GetCurrentUserUseCase(get()) }
    factory { GetUserByIdUseCase(get()) }
    factory { UpdateUserNameUseCase(get()) }
    factory { UpdateUserVictoryMessageUseCase(get()) }
    factory { UpdateUserPictureUseCase(get()) }
    factory { GetRoomPlayersUseCase(get()) }
    factory { CreateUserUseCase(get()) }
    factory { DeleteUserUseCase(get()) }
}
