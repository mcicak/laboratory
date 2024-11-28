package rs.symphony.cicak.webshop.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import rs.symphony.cicak.webshop.presentation.ui.cart.CartScreen
import rs.symphony.cicak.webshop.presentation.ui.cart.CartViewModel
import rs.symphony.cicak.webshop.presentation.ui.categories.CategoriesScreen
import rs.symphony.cicak.webshop.presentation.ui.categories.CategoriesViewModel
import rs.symphony.cicak.webshop.presentation.ui.favorites.FavoritesScreen
import rs.symphony.cicak.webshop.presentation.ui.favorites.FavoritesViewModel
import rs.symphony.cicak.webshop.presentation.ui.product.ProductDetailsScreen
import rs.symphony.cicak.webshop.presentation.ui.products.HomeViewModel
import rs.symphony.cicak.webshop.presentation.ui.products.ProductsScreen

@OptIn(KoinExperimentalAPI::class)
@Composable
fun NavGraph(navController: NavHostController, startDestination: Destination) {

    NavHost(
        modifier = Modifier.background(Transparent),
        navController = navController,
        startDestination = startDestination,
        enterTransition = { push() },
        exitTransition = { slideToParallaxLeft() },
        popEnterTransition = { slideFromParallaxLeft() },
        popExitTransition = { slideToRight() }
    ) {

        composable<HomeDestination> {
            val homeViewModel = koinViewModel<HomeViewModel>()
            ProductsScreen(homeViewModel, onProductClick = { productId ->
                navController.navigate(ProductDetailsDestination(productId))
            })
        }

        composable<ProductsDestination> {
            val args = it.toRoute<ProductsDestination>()
            val homeViewModel = koinViewModel<HomeViewModel>()
            ProductsScreen(homeViewModel, categoryId = args.categoryId, onProductClick = { productId ->
                navController.navigate(ProductDetailsDestination(productId))
            }, onBack = { navController.popBackStack() })
        }

        composable<CategoriesDestination> {
            val categoriesViewModel = koinViewModel<CategoriesViewModel>()
            CategoriesScreen(categoriesViewModel, onCategoryClick = { categoryId ->
                navController.navigate(ProductsDestination(categoryId = categoryId))
            })
        }

        composable<FavoritesDestination> {
            val favoritesViewModel = koinViewModel<FavoritesViewModel>()
            FavoritesScreen(favoritesViewModel, onProductClick = { productId ->
                navController.navigate(ProductDetailsDestination(productId))
            })
        }

        composable<CartDestination> {
            val cartViewModel = koinViewModel<CartViewModel>()
            CartScreen(cartViewModel, onProductClick = { productId ->
                navController.navigate(ProductDetailsDestination(productId))
            })
        }

        composable<ProductDetailsDestination> {
            val args = it.toRoute<ProductDetailsDestination>()
            ProductDetailsScreen(
                productId = args.id,
                onBack = { navController.popBackStack() },
                onRecommendedProductClick = { navController.navigate(ProductDetailsDestination(it)) }
            )
        }
    }
}
