package di

import data.di.dataModule
import domain.di.domainModule
import org.koin.core.context.startKoin
import ui.di.uiModule

fun initKoin() = startKoin {
    modules(
        dataModule,
        domainModule,
        uiModule
    )
}
