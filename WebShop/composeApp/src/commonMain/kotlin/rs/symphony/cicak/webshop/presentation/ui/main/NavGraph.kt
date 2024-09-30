package rs.symphony.cicak.webshop.presentation.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import rs.symphony.cicak.webshop.presentation.ui.home.HomeScreen
import rs.symphony.cicak.webshop.presentation.ui.home.HomeViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeNavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = HomeScreen) {

        composable<HomeScreen> {
            val homeViewModel = koinViewModel<HomeViewModel>()
            HomeScreen(homeViewModel, onProductClick = { productId ->
                navController.navigate(ProductDetailsScreen(productId))
            })
        }

        composable<ProductDetailsScreen> {
            val args = it.toRoute<ProductDetailsScreen>()
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Product Details ${args.id}")
            }
        }
    }
}
