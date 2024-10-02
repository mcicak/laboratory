package rs.symphony.cicak.webshop.di

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import rs.symphony.cicak.webshop.data.repository.AppModel
import rs.symphony.cicak.webshop.data.repository.CartRepository
import rs.symphony.cicak.webshop.data.repository.CartRepositoryFake
import rs.symphony.cicak.webshop.data.repository.CategoryRepository
import rs.symphony.cicak.webshop.data.repository.CategoryRepositoryFake
import rs.symphony.cicak.webshop.data.repository.ProductRepository
import rs.symphony.cicak.webshop.data.repository.ProductRepositoryFake
import rs.symphony.cicak.webshop.dependencies.MyRepository
import rs.symphony.cicak.webshop.dependencies.MyRepositoryImpl
import rs.symphony.cicak.webshop.dependencies.MyViewModel
import rs.symphony.cicak.webshop.domain.ProductId
import rs.symphony.cicak.webshop.presentation.ui.cart.CartViewModel
import rs.symphony.cicak.webshop.presentation.ui.categories.CategoriesViewModel
import rs.symphony.cicak.webshop.presentation.ui.favorites.FavoritesViewModel
import rs.symphony.cicak.webshop.presentation.ui.home.HomeViewModel
import rs.symphony.cicak.webshop.presentation.ui.product.ProductViewModel

expect val platformModule: Module

val sharedModule = module {

    // Repositories
    singleOf(::AppModel)
    singleOf(::MyRepositoryImpl).bind<MyRepository>()
    singleOf(::CategoryRepositoryFake).bind<CategoryRepository>()
    singleOf(::ProductRepositoryFake).bind<ProductRepository>()
    singleOf(::CartRepositoryFake).bind<CartRepository>()

    // View Models
    viewModelOf(::MyViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::CartViewModel)

    viewModel { (productId: ProductId) ->
        ProductViewModel(
            productRepository = get(),
            cartRepository = get(),
            productId = productId
        )
    }
}
