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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import rs.symphony.cicak.webshop.data.repository.AppModel
import rs.symphony.cicak.webshop.domain.User
import rs.symphony.cicak.webshop.presentation.util.isApple
import webshop.composeapp.generated.resources.Res
import webshop.composeapp.generated.resources.login_background

@Composable
@Preview
fun App() {
    val WebClientId = "1055127789850-96lt2811ro71avvaji0cn8idbm94t9aj.apps.googleusercontent.com"
    GoogleAuthProvider.create(credentials = GoogleAuthCredentials(serverId = WebClientId))

    MyAppTheme {
        KoinContext {
            val appModel: AppModel = koinInject()
            val user = appModel.user.collectAsState()

            val auth = remember { Firebase.auth }

            LaunchedEffect(auth.currentUser) {


                auth.currentUser?.let { firebaseUser ->
                    firebaseUser.email?.let { email ->
                        appModel.setUser(
                            User(firebaseUser.displayName ?: "N/A", email)
                        )
                    }
                } ?: appModel.setUser(null)
            }

            if (user.value == null) {
                LoginScreen(appModel = appModel) { result ->
                    val firebaseUser = result.getOrNull()

                    firebaseUser?.let { fbUser ->
                        fbUser.email?.let { email ->
                            appModel.setUser(
                                User(fbUser.displayName ?: "N/A", email)
                            )
                        }
                    }
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
    appModel: AppModel,
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
//            ),
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
