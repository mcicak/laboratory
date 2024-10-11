package rs.symphony.cicak.webshop.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import rs.symphony.cicak.webshop.presentation.ui.categories.CategoriesScreen
import rs.symphony.cicak.webshop.presentation.ui.categories.CategoriesViewModel
import rs.symphony.cicak.webshop.presentation.ui.product.ProductDetailsScreen
import rs.symphony.cicak.webshop.presentation.ui.products.HomeViewModel
import rs.symphony.cicak.webshop.presentation.ui.products.ProductsScreen

@OptIn(KoinExperimentalAPI::class)
@Composable
fun NavGraph(navController: NavHostController, startDestination: Destination) {

    NavHost(modifier = Modifier.background(Transparent),navController = navController, startDestination = startDestination) {

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
