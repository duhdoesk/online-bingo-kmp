package di

import data.di.dataModule
import domain.di.domainModule
import org.koin.core.context.startKoin

fun initKoin() =
    startKoin {
        modules(domainModule, dataModule)
    }