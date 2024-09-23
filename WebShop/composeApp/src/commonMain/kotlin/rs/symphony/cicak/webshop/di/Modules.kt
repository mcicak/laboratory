package rs.symphony.cicak.webshop.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import rs.symphony.cicak.webshop.dependencies.MyRepository
import rs.symphony.cicak.webshop.dependencies.MyRepositoryImpl
import rs.symphony.cicak.webshop.dependencies.MyViewModel
import rs.symphony.cicak.webshop.presentation.ui.home.HomeViewModel
import rs.symphony.cicak.webshop.presentation.ui.categories.CategoriesViewModel

expect val platformModule: Module

val sharedModule = module {

    singleOf(::MyRepositoryImpl).bind<MyRepository>()
    viewModelOf(::MyViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CategoriesViewModel)
}