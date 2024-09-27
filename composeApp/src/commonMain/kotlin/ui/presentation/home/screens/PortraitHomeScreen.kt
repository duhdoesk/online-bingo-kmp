package ui.presentation.home.screens

import OperationalSystem
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions
import domain.room.model.BingoType
import getPlatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.become_vip_button
import themedbingo.composeapp.generated.resources.bingo_balls
import themedbingo.composeapp.generated.resources.classic_bingo
import themedbingo.composeapp.generated.resources.classic_bingo_desc
import themedbingo.composeapp.generated.resources.full_exp
import themedbingo.composeapp.generated.resources.hw_blue_bg
import themedbingo.composeapp.generated.resources.hw_green_bg
import themedbingo.composeapp.generated.resources.hw_orange_bg
import themedbingo.composeapp.generated.resources.premium_crown
import themedbingo.composeapp.generated.resources.smiling_squirrel
import themedbingo.composeapp.generated.resources.themed_bingo
import themedbingo.composeapp.generated.resources.themed_bingo_desc
import themedbingo.composeapp.generated.resources.vip_description
import themedbingo.composeapp.generated.resources.vip_pass
import ui.navigation.Configuration
import ui.presentation.common.components.NoPictureHWBanner
import ui.presentation.home.event.HomeScreenEvent
import ui.presentation.home.screens.component.BingoTypeCard
import ui.presentation.home.screens.component.HomeScreenHello
import ui.presentation.home.screens.component.HomeScreenTopBar
import ui.presentation.home.state.HomeScreenUIState
import ui.presentation.util.bottom_sheet.PaywallBottomSheet

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PortraitHomeScreen(
    uiState: HomeScreenUIState,
    event: (event: HomeScreenEvent) -> Unit,
) {
    var showPaywall by remember { mutableStateOf(false) }
    val paywallBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val paywallOptions = remember {
        PaywallOptions(dismissRequest = { showPaywall = false }) {
            shouldDisplayDismissButton = true
        }
    }

    Scaffold(modifier = Modifier.imePadding()) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .systemBarsPadding()
                .fillMaxSize(),
        ) {
            Column(modifier = Modifier.sizeIn(maxWidth = 600.dp, maxHeight = 1000.dp)) {
                HomeScreenTopBar(
                    isSubscribed = uiState.isSubscribed,
                    onClickSettings = { event(HomeScreenEvent.Navigate(Configuration.ProfileScreen)) },
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth(),
                    userName = uiState.userName,
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                ) {
//                    HomeScreenHello(
//                        userName = uiState.userName,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Modos de Jogo",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    )

                    Spacer(Modifier.height(4.dp))

                    BingoTypeCard(
                        background = Res.drawable.hw_green_bg,
                        icon = Res.drawable.smiling_squirrel,
                        title = Res.string.themed_bingo,
                        body = Res.string.themed_bingo_desc,
                        onClick = {
                            event(
                                HomeScreenEvent.Navigate(
                                    Configuration.JoinScreen(
                                        BingoType.THEMED
                                    )
                                )
                            )
                        },
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    Spacer(Modifier.height(8.dp))

                    BingoTypeCard(
                        background = Res.drawable.hw_blue_bg,
                        icon = Res.drawable.bingo_balls,
                        title = Res.string.classic_bingo,
                        body = Res.string.classic_bingo_desc,
                        onClick = {
                            event(
                                HomeScreenEvent.Navigate(
                                    Configuration.JoinScreen(
                                        BingoType.CLASSIC
                                    )
                                )
                            )
                        },
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    if (!uiState.isSubscribed) {
                        Spacer(Modifier.height(24.dp))

                        Text(
                            text = stringResource(Res.string.full_exp),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                        )

                        Spacer(Modifier.height(4.dp))

                        BingoTypeCard(
                            background = Res.drawable.hw_orange_bg,
                            icon = Res.drawable.premium_crown,
                            title = Res.string.vip_pass,
                            body = Res.string.vip_description,
                            onClick = { showPaywall = true },
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                    }
                }

                NoPictureHWBanner(modifier = Modifier.padding(16.dp).fillMaxWidth())
            }

            if (showPaywall) {
                when (getPlatform().system) {
                    OperationalSystem.ANDROID ->
                        PaywallBottomSheet(
                            paywallOptions = paywallOptions,
                            sheetState = paywallBottomSheetState,
                            onDismiss = { showPaywall = false },
                        )

                    OperationalSystem.IOS ->
                        AnimatedVisibility(
                            visible = showPaywall,
                            enter = expandVertically(tween(700)),
                            exit = shrinkVertically(tween(700)),
                            modifier = Modifier.align(Alignment.BottomCenter),
                        ) { Paywall(paywallOptions) }
                }
            }
        }
    }
}