package rs.symphony.cicak.webshop.presentation.ui.main

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    MyAppTheme {
        KoinContext {
            WebShopApp()
        }
    }
}
