package rs.symphony.cicak.webshop.presentation.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mmk.kmpauth.firebase.apple.AppleButtonUiContainer
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.mmk.kmpauth.uihelper.apple.AppleSignInButton
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import io.kamel.core.config.DefaultCacheSize
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.fileFetcher
import io.kamel.core.config.httpFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.kamel.image.config.imageBitmapDecoder
import io.kamel.image.config.imageVectorDecoder
import io.kamel.image.config.svgDecoder
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.http.isSuccess
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import rs.symphony.cicak.webshop.presentation.util.isApple
import webshop.composeapp.generated.resources.Res
import webshop.composeapp.generated.resources.login_background

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    val WebClientId = "1055127789850-96lt2811ro71avvaji0cn8idbm94t9aj.apps.googleusercontent.com"
    GoogleAuthProvider.create(credentials = GoogleAuthCredentials(serverId = WebClientId))

    val viewModel = koinViewModel<RootViewModel>()

    MyAppTheme {
        KoinContext {
            val user = viewModel.user.collectAsState()
            val auth = remember { Firebase.auth }

            LaunchedEffect(Unit) {
                KamelConfig {
                    takeFrom(KamelConfig.Default)

                    // Sets the number of images to cache
                    imageBitmapCacheSize = DefaultCacheSize

                    // adds an ImageBitmapDecoder
                    imageBitmapDecoder()

                    // adds an ImageVectorDecoder (XML)
                    imageVectorDecoder()

                    // adds an SvgDecoder (SVG)
                    svgDecoder()

                    // adds a FileFetcher
                    fileFetcher()

                    // Configures Ktor HttpClient
                    httpFetcher {
                        // httpCache is defined in kamel-core and configures the ktor client
                        // to install a HttpCache feature with the implementation provided by Kamel.
                        // The size of the cache can be defined in Bytes.
                        httpCache(10 * 1024 * 1024  /* 10 MiB */)

//                        defaultRequest {
//                            url("https://firebasestorage.googleapis.com/")
//                            cacheControl(CacheControl.MaxAge(maxAgeSeconds = 10000))
//                        }

                        install(HttpRequestRetry) {
                            maxRetries = 3
                            retryIf { httpRequest, httpResponse ->
                                !httpResponse.status.isSuccess()
                            }
                        }

                        // Requires adding "io.ktor:ktor-client-logging:$ktor_version"
//                        Logging {
//                            level = LogLevel.INFO
//                            logger = Logger.SIMPLE
//                        }
                    }
                }

                auth.currentUser?.let { firebaseUser ->
                    viewModel.handleUserLogin(firebaseUser)
                }
            }

            if (user.value == null) {
                LoginScreen { result ->
                    val firebaseUser = result.getOrNull()
                    firebaseUser?.let { viewModel.handleUserLogin(it) }
                }
            } else {
                WebShopApp()
            }
        }
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onFirebaseResult: (Result<FirebaseUser?>) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(Color(0xFF1a1147), Color(0xFFd83270)), // Pink to cyan/blue
//                    startY = 0f,
//                    endY = Float.POSITIVE_INFINITY
//                )
//            )
        ,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(Res.drawable.login_background),
            contentDescription = null,
            contentScale = ContentScale.FillHeight
        )

        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Neon title
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                text = "80s Web Shop",
                style = MaterialTheme.typography.h1.copy(
                    color = Color(0xFF00FFDD), // Neon cyan color
                    shadow = Shadow(
                        color = Color(0xFF00FFFF), // Neon glow effect
                        blurRadius = 20f,
                        offset = Offset(0f, 0f)
                    )
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(0.8f))

            // Google Sign-In Button and authentication with Firebase
            GoogleButtonUiContainerFirebase(onResult = onFirebaseResult) {
                GoogleSignInButton(modifier = Modifier.fillMaxWidth()) { this.onClick() }
            }

            if (isApple()) {
                Spacer(modifier = Modifier.height(20.dp))
                AppleButtonUiContainer(onResult = onFirebaseResult) {
                    AppleSignInButton(modifier = Modifier.fillMaxWidth()) { this.onClick() }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
