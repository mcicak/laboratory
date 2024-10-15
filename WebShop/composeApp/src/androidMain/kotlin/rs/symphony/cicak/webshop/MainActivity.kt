package rs.symphony.cicak.webshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import com.mmk.kmpnotifier.permission.permissionUtil
import rs.symphony.cicak.webshop.presentation.ui.main.App

class MainActivity : ComponentActivity() {

    private val permissionUtil by permissionUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()

        FirebaseApp.initializeApp(this)

        setContent {
            App()
        }

        permissionUtil.askNotificationPermission()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}