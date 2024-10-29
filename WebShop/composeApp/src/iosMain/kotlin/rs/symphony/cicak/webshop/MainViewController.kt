package rs.symphony.cicak.webshop

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.compose.KoinContext
import platform.UIKit.UIViewController
import rs.symphony.cicak.webshop.di.AppInitializer
import rs.symphony.cicak.webshop.presentation.ui.main.App
import rs.symphony.cicak.webshop.presentation.ui.main.MyAppTheme
import rs.symphony.cicak.webshop.presentation.ui.product.ProductDetailsScreen

fun MainViewController() = ComposeUIViewController(
    configure = {
        AppInitializer.initialize()
    }
) {
    App()
}

fun ProductDetailsViewController(productId: String, parent: UIViewController) = ComposeUIViewController {
    MyAppTheme {
        KoinContext {
            ProductDetailsScreen(
                productId = productId,
                onBack = { parent.dismissViewControllerAnimated(true) {} },
                onRecommendedProductClick = { recommendedId ->

                }
            )
        }
    }
}
