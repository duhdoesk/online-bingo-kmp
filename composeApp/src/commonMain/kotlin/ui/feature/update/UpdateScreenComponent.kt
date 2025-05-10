package ui.feature.update

import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.KoinComponent

class UpdateScreenComponent(
    componentContext: ComponentContext,
    val updateUrl: String
) : ComponentContext by componentContext, KoinComponent
