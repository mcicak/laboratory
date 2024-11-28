@file:OptIn(ExperimentalTestApi::class)

package rs.symphony.cicak.webshop.dependencies

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import rs.symphony.cicak.webshop.presentation.ui.xtest.Counter
import kotlin.test.Test

class CounterTest {

    @Test
    fun testCountingUp() = runComposeUiTest {
        setContent {
            Counter()
        }

        onNodeWithText("0").assertExists()
        onNodeWithText("1").assertDoesNotExist()

        onNodeWithText("Increment").performClick()

        onNodeWithText("0").assertDoesNotExist()
        onNodeWithText("1").assertExists()
    }
}