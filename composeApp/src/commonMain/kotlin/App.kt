import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.revenuecat.purchases.kmp.CustomerInfo
import ui.theme.AppTheme
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.configure
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import ui.navigation.RootComponent
import ui.presentation.util.getAsyncImageLoader
import ui.presentation.util.rememberWindowInfo

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App(rootComponent: RootComponent) {
    KoinContext {
        AppTheme {

            /**
             * Child Stack representation - navigation
             */
            val childStack by rootComponent.childStack.subscribeAsState()

            /**
             * Coil Setup
             */
            setSingletonImageLoaderFactory { context ->
                getAsyncImageLoader(context)
            }

            /**
             * Revenue Cat Setup
             */
            Purchases.logLevel = LogLevel.DEBUG
            Purchases.configure(apiKey = getPlatform().revCatApiKey)

            LaunchedEffect(Unit) {
                coroutineScope {
                    rootComponent.user.collect { user ->
                        if (user != null) {
                            Purchases.sharedInstance.logIn(
                                newAppUserID = user.id,
                                onError = {},
                                onSuccess = { customerInfo: CustomerInfo, b: Boolean -> }
                            )
                        }
                    }
                }
            }

            /**
             * UI Setup
             */
            Scaffold(modifier = Modifier.imePadding()) {

                BoxWithConstraints(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize().systemBarsPadding(),
                ) {

                    val windowInfo = rememberWindowInfo(
                        screenHeight = this.maxHeight,
                        screenWidth = this.maxWidth,
                    )

                    Children(
                        stack = childStack,
                        animation = stackAnimation(slide()),
                    ) { child ->

                        CreateScreen(
                            instance = child.instance,
                            windowInfo = windowInfo,
                        )
                    }
                }
            }
        }
    }
}