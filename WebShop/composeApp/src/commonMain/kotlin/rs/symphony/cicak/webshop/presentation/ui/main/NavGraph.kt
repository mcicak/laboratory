package rs.symphony.cicak.webshop.presentation.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import rs.symphony.cicak.webshop.presentation.ui.categories.CategoriesScreen
import rs.symphony.cicak.webshop.presentation.ui.categories.CategoriesViewModel
import rs.symphony.cicak.webshop.presentation.ui.product.ProductDetailsScreen
import rs.symphony.cicak.webshop.presentation.ui.products.HomeViewModel
import rs.symphony.cicak.webshop.presentation.ui.products.ProductsScreen

@OptIn(KoinExperimentalAPI::class)
@Composable
fun NavGraph(navController: NavHostController, startDestination: String) {

    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = "start") {
            val homeViewModel = koinViewModel<HomeViewModel>()
            ProductsScreen(homeViewModel, onProductClick = { productId ->
                navController.navigate("product_details/$productId")
            })
        }

        composable(route = "products/{categoryId}") { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            val homeViewModel = koinViewModel<HomeViewModel>()
            ProductsScreen(homeViewModel, categoryId = categoryId, onProductClick = { productId ->
                navController.navigate("product_details/$productId")
            }, onBack = { navController.popBackStack() })
        }

        composable(route = "categories") {
            val categoriesViewModel = koinViewModel<CategoriesViewModel>()
            CategoriesScreen(categoriesViewModel, onCategoryClick = { categoryId ->
                navController.navigate("products/$categoryId")
            })
        }

        composable(route = "product_details/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            ProductDetailsScreen(
                productId = productId ?: "",
                onBack = { navController.popBackStack() },
                onRecommendedProductClick = { recommendedProductId ->
                    navController.navigate("product_details/$recommendedProductId")
                }
            )
        }
    }
}
