package rs.symphony.cicak.webshop.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.core.module.dsl.singleOf
import rs.symphony.cicak.webshop.dependencies.DBClient

actual val platformModule = module {
    singleOf(::DBClient)
}