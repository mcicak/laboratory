package rs.symphony.cicak.webshop.presentation.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import rs.symphony.cicak.webshop.presentation.ui.home.HomeScreen
import rs.symphony.cicak.webshop.presentation.ui.home.HomeViewModel
import rs.symphony.cicak.webshop.presentation.ui.product.ProductDetailsScreen

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeNavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = HomeDestination) {

        composable<HomeDestination> {
            val homeViewModel = koinViewModel<HomeViewModel>()
            HomeScreen(homeViewModel, onProductClick = { productId ->
                navController.navigate(ProductDetailsDestination(productId))
            })
        }

        composable<ProductDetailsDestination> {
            val args = it.toRoute<ProductDetailsDestination>()
            ProductDetailsScreen(args.id, onBack = { navController.popBackStack() })
        }
    }
}
