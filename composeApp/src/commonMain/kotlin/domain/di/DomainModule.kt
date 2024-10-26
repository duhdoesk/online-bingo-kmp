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
import domain.auth.supabase.use_case.SignInWithGoogleUseCase
import domain.auth.supabase.use_case.SupabaseDeleteAccountUseCase
import domain.auth.supabase.use_case.SupabaseSignOutUseCase
import domain.auth.use_case.ChangePasswordWithReAuthenticationUseCase
import domain.auth.use_case.DeleteAccountUseCase
import domain.auth.use_case.SignOutUseCase
import domain.billing.SubscribeToUserSubscriptionData
import domain.card.repository.CardRepository
import domain.card.use_case.FlowCardByRoomAndUserIDUseCase
import domain.card.use_case.SetCardByRoomAndUserIDUseCase
import domain.character.repository.CharacterRepository
import domain.room.repository.BingoRoomRepository
import domain.room.use_case.CallBingoUseCase
import domain.room.use_case.CreateRoomUseCase
import domain.room.use_case.FlowRoomByIdUseCase
import domain.room.use_case.GetBingoStyleUseCase
import domain.room.use_case.GetNotStartedRoomsUseCase
import domain.room.use_case.GetRoomByIdUseCase
import domain.room.use_case.GetRoomsUseCase
import domain.room.use_case.GetRunningRoomsUseCase
import domain.room.use_case.JoinRoomUseCase
import domain.room.use_case.MapRoomDTOToModelUseCase
import domain.room.use_case.RaffleNextItemUseCase
import domain.room.use_case.UpdateRoomStateUseCase
import domain.theme.repository.BingoThemeRepository
import domain.theme.use_case.FlowThemeByIdUseCase
import domain.theme.use_case.GetAllThemesUseCase
import domain.character.use_case.ObserveThemeCharacters
import domain.character.use_case.GetRoomCharacters
import domain.theme.use_case.GetRoomThemeUseCase
import domain.theme.use_case.GetThemeByIdUseCase
import domain.character.use_case.GetThemeCharacters
import domain.theme.use_case.ObserveAvailableThemes
import domain.user.repository.UserRepository
import domain.user.use_case.CheckIfIsNewUserUseCase
import domain.user.use_case.CreateUserUseCase
import domain.user.use_case.DeleteUserUseCase
import domain.user.use_case.FlowUserUseCase
import domain.user.use_case.GetProfilePicturesUseCase
import domain.user.use_case.GetRoomPlayersUseCase
import domain.user.use_case.GetUserByIdUseCase
import domain.user.use_case.UpdateNameUseCase
import domain.user.use_case.UpdateUserPictureUseCase
import domain.user.use_case.UpdateVictoryMessageUseCase
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
    single<FlowUserUseCase> { FlowUserUseCase(get()) }
    single<UpdateNameUseCase> { UpdateNameUseCase(get()) }
    single<UpdateVictoryMessageUseCase> { UpdateVictoryMessageUseCase(get()) }
    single<UpdateUserPictureUseCase> { UpdateUserPictureUseCase(get()) }
    single<GetProfilePicturesUseCase> { GetProfilePicturesUseCase(get(), get()) }
    single<GetRoomPlayersUseCase> { GetRoomPlayersUseCase(get(), get()) }
    single<CreateUserUseCase> { CreateUserUseCase(get()) }
    single<DeleteUserUseCase> { DeleteUserUseCase(get()) }
    single<CheckIfIsNewUserUseCase> { CheckIfIsNewUserUseCase(get()) }

//    Auth
    single<AuthService> { AuthServiceImpl(get()) }
    single<DeleteAccountUseCase> { DeleteAccountUseCase(get()) }
    single<SignOutUseCase> { SignOutUseCase(get()) }
    single<ChangePasswordWithReAuthenticationUseCase> { ChangePasswordWithReAuthenticationUseCase(get()) }

//    Supabase Auth
    single<SupabaseAuthService> { SupabaseAuthServiceImpl(get()) }
    single<SignInWithGoogleUseCase> { SignInWithGoogleUseCase(get(), get()) }
    single<SupabaseSignOutUseCase> { SupabaseSignOutUseCase(get()) }
    single<SupabaseDeleteAccountUseCase> { SupabaseDeleteAccountUseCase(get()) }

//    Billing
    single<SubscribeToUserSubscriptionData> { SubscribeToUserSubscriptionData() }
}