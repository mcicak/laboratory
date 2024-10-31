package rs.symphony.cicak.webshop

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import com.mmk.kmpnotifier.extensions.onCreateOrOnNewIntent
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.permission.permissionUtil
import org.koin.compose.KoinContext
import rs.symphony.cicak.webshop.presentation.ui.main.App
import rs.symphony.cicak.webshop.presentation.ui.main.MyAppTheme
import rs.symphony.cicak.webshop.presentation.ui.product.ProductDetailsScreen

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
        NotifierManager.onCreateOrOnNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        NotifierManager.onCreateOrOnNewIntent(intent)
    }
}

class ProductDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productId = intent.getStringExtra("productId") ?: ""

        setContent {
            MyAppTheme {
                KoinContext {
                    ProductDetailsScreen(
                        productId = productId,
                        onBack = { finish() },
                        onRecommendedProductClick = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
