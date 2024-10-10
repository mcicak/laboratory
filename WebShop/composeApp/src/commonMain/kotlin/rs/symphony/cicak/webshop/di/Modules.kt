package rs.symphony.cicak.webshop.di

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import rs.symphony.cicak.webshop.data.repository.AppModel
import rs.symphony.cicak.webshop.data.repository.CartRepository
import rs.symphony.cicak.webshop.data.repository.CartRepositoryImpl
import rs.symphony.cicak.webshop.data.repository.CategoryRepository
import rs.symphony.cicak.webshop.data.repository.CategoryRepositoryFirestore
import rs.symphony.cicak.webshop.data.repository.ProductRepository
import rs.symphony.cicak.webshop.data.repository.ProductRepositoryImpl
import rs.symphony.cicak.webshop.data.repository.UserRepository
import rs.symphony.cicak.webshop.data.repository.UserRepositoryImpl
import rs.symphony.cicak.webshop.dependencies.MyRepository
import rs.symphony.cicak.webshop.dependencies.MyRepositoryImpl
import rs.symphony.cicak.webshop.dependencies.MyViewModel
import rs.symphony.cicak.webshop.domain.ProductId
import rs.symphony.cicak.webshop.presentation.ui.cart.CartViewModel
import rs.symphony.cicak.webshop.presentation.ui.categories.CategoriesViewModel
import rs.symphony.cicak.webshop.presentation.ui.favorites.FavoritesViewModel
import rs.symphony.cicak.webshop.presentation.ui.products.HomeViewModel
import rs.symphony.cicak.webshop.presentation.ui.main.RootViewModel
import rs.symphony.cicak.webshop.presentation.ui.product.ProductViewModel

expect val platformModule: Module

val sharedModule = module {

    // Repositories
    singleOf(::AppModel)
    singleOf(::MyRepositoryImpl).bind<MyRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::CategoryRepositoryFirestore).bind<CategoryRepository>()
    singleOf(::ProductRepositoryImpl).bind<ProductRepository>()
    singleOf(::CartRepositoryImpl).bind<CartRepository>()

    // View Models
    viewModelOf(::MyViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::RootViewModel)

    viewModel { (productId: ProductId) ->
        ProductViewModel(
            productRepository = get(),
            cartRepository = get(),
            userRepository = get(),
            productId = productId
        )
    }
}
