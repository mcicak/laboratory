package com.facebook.transitions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.facebook.transitions.ui.theme.TransitionsTheme

val fontSize = 100.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TransitionsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Root()
                }
            }
        }
    }
}

@Composable
fun Root() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable(
            exitTransition = {
                when (targetState.destination.route) {
                    "feedbackType" -> stayStill()
                    else -> slideToParallaxLeft()
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "feedbackType" -> stayStillEnter()
                    "feedbackSent" -> stayStillEnter()
                    else -> slideFromParallaxLeft()
                }
            },
            route = "main"
        ) {
            MainScreen(navController)
        }

        composable(
            route = "collection",
            enterTransition = { push() },
            exitTransition = { slideToParallaxLeft() },
            popEnterTransition = { push() },
            popExitTransition = { slideToRight() }
        ) {
            CollectionScreen(navController)
        }

        composable(
            route = "challenge",
            enterTransition = { push() },
            exitTransition = { slideToParallaxLeft() },
            popEnterTransition = { push() },
            popExitTransition = { slideToRight() }
        ) {
            ChallengeScreen(navController)
        }

        composable(
            route = "task",
            enterTransition = { push() },
            exitTransition = { slideToParallaxLeft() },
            popEnterTransition = { push() },
            popExitTransition = { slideToRight() }
        ) {
            TaskScreen(navController)
        }

        composable(
            route = "feedbackType",
            enterTransition = { present() },
            exitTransition = {
                when (targetState.destination.route) {
                    "main" -> dismiss()
                    else -> slideToParallaxLeft()
                }
            },
            popEnterTransition = {
                slideFromParallaxLeft()
            },
            popExitTransition = { dismiss() }
        ) {
            FeedbackTypeScreen(navController)
        }

        composable(
            route = "feedbackInput",
            enterTransition = { push() },
            exitTransition = { slideToParallaxLeft() },
            popEnterTransition = { push() },
            popExitTransition = { slideToRight() }
        ) {
            FeedbackInputScreen(navController)
        }

        composable(
            route = "feedbackSent",
            enterTransition = { push() },
            exitTransition = { dismiss() }
        ) {
            BackHandler {}
            FeedbackSentScreen(navController)
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffcccff2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(text = "1", fontSize = fontSize)

        Button(onClick = {
            navController.navigate("feedbackType")
        }) {
            Text("Show Modal")
        }

        Button(onClick = {
            navController.navigate("collection")
        }) {
            Text("Show Push")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun FeedbackTypeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffcccff2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(text = "M1", fontSize = fontSize)

        Button(onClick = {
            navController.navigate("feedbackInput")
        }) {
            Text("Next")
        }

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Close")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun FeedbackInputScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffcccff2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(text = "M2", fontSize = fontSize)

        Button(onClick = {
            navController.navigate("feedbackSent")
        }) {
            Text("Next")
        }

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Back")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun FeedbackSentScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffcccff2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(text = "M3", fontSize = fontSize)

        Button(onClick = {
            navController.popBackStack("main", inclusive = false)
        }) {
            Text("Close")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun CollectionScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffcccff2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(text = "2", fontSize = fontSize)

        Button(onClick = {
            navController.navigate("challenge")
        }) {
            Text("Next")
        }

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Back")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ChallengeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffcccff2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(text = "3", fontSize = fontSize)

        Button(onClick = {
            navController.navigate("task")
        }) {
            Text("Next")
        }

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Back")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun TaskScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffcccff2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(text = "4", fontSize = fontSize)

        Button(onClick = {
            navController.popBackStack("main", inclusive = false)
        }) {
            Text("CLOSE")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
