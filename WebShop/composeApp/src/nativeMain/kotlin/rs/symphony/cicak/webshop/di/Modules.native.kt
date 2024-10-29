package rs.symphony.cicak.webshop.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import rs.symphony.cicak.webshop.dependencies.DBClient
import rs.symphony.cicak.webshop.dependencies.NotificationHandler

actual val platformModule = module {
    singleOf(::DBClient)
    single { NotificationHandler() }
}
