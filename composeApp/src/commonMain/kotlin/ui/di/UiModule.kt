package ui.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ui.feature.profile.component.picture.ChangePictureViewModel

val uiModule = module {
    viewModel { ChangePictureViewModel(get()) }
}
