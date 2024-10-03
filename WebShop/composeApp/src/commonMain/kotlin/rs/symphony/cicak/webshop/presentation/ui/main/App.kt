package rs.symphony.cicak.webshop.presentation.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI
import rs.symphony.cicak.webshop.data.repository.AppModel
import rs.symphony.cicak.webshop.domain.User

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    MyAppTheme {
        KoinContext {
            val appModel: AppModel = koinInject()

            val user = appModel.user.collectAsState()
            
            // if user not logged show login screen
            if (user.value == null) {
                LoginScreen(appModel = appModel)
            } else {
                WebShopApp()
            }
        }
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier, appModel: AppModel) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(text = "Login Screen")

            Button(onClick = {
                appModel.setUser(User("John Doe", "john.doe@example.com"))
            }) {
                Text(text = "Login")
            }
        }
    }
}