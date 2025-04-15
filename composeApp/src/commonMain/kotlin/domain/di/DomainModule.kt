package domain.di

import data.auth.AuthServiceImpl
import data.auth.supabase.SupabaseAuthServiceImpl
import data.card.repository.CardRepositoryImpl
import data.character.repository.CharacterRepositoryImpl
import data.room.repository.BingoRoomRepositoryImpl
import data.theme.repository.BingoThemeRepositoryImpl
import data.user.repository.UserRepositoryImpl
import domain.auth.AuthService
import domain.auth.supabase.SupabaseAuthService
import domain.auth.supabase.useCase.SupabaseDeleteAccountUseCase
import domain.auth.supabase.useCase.SupabaseSignOutUseCase
import domain.auth.useCase.ChangePasswordWithReAuthenticationUseCase
import domain.auth.useCase.DeleteAccountUseCase
import domain.auth.useCase.SignOutUseCase
import domain.billing.SubscribeToUserSubscriptionData
import domain.card.repository.CardRepository
import domain.card.useCase.FlowCardByRoomAndUserIDUseCase
import domain.card.useCase.SetCardByRoomAndUserIDUseCase
import domain.character.repository.CharacterRepository
import domain.character.useCase.GetRoomCharacters
import domain.character.useCase.GetThemeCharacters
import domain.character.useCase.ObserveThemeCharacters
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
import domain.user.repository.UserRepository
import domain.user.useCase.CheckIfIsNewUserUseCase
import domain.user.useCase.CreateUserUseCase
import domain.user.useCase.DeleteUserUseCase
import domain.user.useCase.GetProfilePicturesUseCase
import domain.user.useCase.GetRoomPlayersUseCase
import domain.user.useCase.GetUserByIdUseCase
import domain.user.useCase.ObserveUserUseCase
import domain.user.useCase.UpdateNameUseCase
import domain.user.useCase.UpdateUserPictureUseCase
import domain.user.useCase.UpdateVictoryMessageUseCase
import org.koin.dsl.module

val domainModule = module {

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
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<GetUserByIdUseCase> { GetUserByIdUseCase(get()) }
    single<ObserveUserUseCase> { ObserveUserUseCase(get()) }
    single<UpdateNameUseCase> { UpdateNameUseCase(get()) }
    single<UpdateVictoryMessageUseCase> { UpdateVictoryMessageUseCase(get()) }
    single<UpdateUserPictureUseCase> { UpdateUserPictureUseCase(get()) }
    single<GetProfilePicturesUseCase> { GetProfilePicturesUseCase(get(), get()) }
    single<GetRoomPlayersUseCase> { GetRoomPlayersUseCase(get(), get()) }
    single<CreateUserUseCase> { CreateUserUseCase(get()) }
    single<DeleteUserUseCase> { DeleteUserUseCase(get()) }
    single<CheckIfIsNewUserUseCase> { CheckIfIsNewUserUseCase(get()) }

//    Audio
    includes(audioModule)

//    Auth
    single<AuthService> { AuthServiceImpl(get()) }
    single<DeleteAccountUseCase> { DeleteAccountUseCase(get()) }
    single<SignOutUseCase> { SignOutUseCase(get()) }
    single<ChangePasswordWithReAuthenticationUseCase> { ChangePasswordWithReAuthenticationUseCase(get()) }

//    Supabase Auth
    single<SupabaseAuthService> { SupabaseAuthServiceImpl(get()) }
    single<SupabaseSignOutUseCase> { SupabaseSignOutUseCase(get()) }
    single<SupabaseDeleteAccountUseCase> { SupabaseDeleteAccountUseCase(get()) }

//    Billing
    single<SubscribeToUserSubscriptionData> { SubscribeToUserSubscriptionData() }
}
