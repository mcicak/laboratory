package rs.symphony.cicak.webshop

import androidx.compose.ui.window.ComposeUIViewController
import rs.symphony.cicak.webshop.di.AppInitializer
import rs.symphony.cicak.webshop.presentation.ui.main.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        AppInitializer.initialize()
    }
) {
    App()
}