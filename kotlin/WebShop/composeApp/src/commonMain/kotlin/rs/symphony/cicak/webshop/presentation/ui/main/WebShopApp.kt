package rs.symphony.cicak.webshop.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import rs.symphony.cicak.webshop.presentation.ui.cart.CartViewModel
import rs.symphony.cicak.webshop.presentation.ui.profile.ProfileScreen
import rs.symphony.cicak.webshop.presentation.util.getPlatformPadding

@OptIn(KoinExperimentalAPI::class)
@Composable
fun WebShopApp() {
    var selectedTab by remember { mutableStateOf(0) }

    val homeNavController = rememberNavController()
    val categoriesNavController = rememberNavController()
    val favoritesNavController = rememberNavController()
    val cartNavController = rememberNavController()
    val profileNavController = rememberNavController()

    val cartViewModel = koinViewModel<CartViewModel>()
    val totalCartItemCount by cartViewModel.totalCartItemCount.collectAsState()

    val insets = WindowInsets.systemBars.asPaddingValues()
    Box(modifier = Modifier.fillMaxSize().synthwaveGrid(selectedTab)) {
        Scaffold(
            modifier = Modifier.padding(top = insets.calculateTopPadding(), bottom = insets.calculateBottomPadding()),
            backgroundColor = Transparent,
            bottomBar = {
                BottomNavigation(backgroundColor = BlueDark3) {
                    TabItem(selectedTab == 0, Icons.Default.Home) { selectedTab = 0 }
                    TabItem(selectedTab == 1, Icons.AutoMirrored.Filled.List) { selectedTab = 1 }
                    TabItem(selectedTab == 2, Icons.Default.Favorite) { selectedTab = 2 }
                    TabItem(selectedTab == 3, Icons.Default.ShoppingCart, badgeCount = totalCartItemCount) { selectedTab = 3 }
                    TabItem(selectedTab == 4, Icons.Default.Person) { selectedTab = 4 }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ) {
                when (selectedTab) {
                    0 -> NavGraph(homeNavController, HomeDestination)
                    1 -> NavGraph(categoriesNavController, CategoriesDestination)
                    2 -> NavGraph(favoritesNavController, FavoritesDestination)
                    3 -> NavGraph(cartNavController, CartDestination)
                    4 -> ProfileScreen()
                }
            }
        }
    }
}

@Composable
private fun RowScope.TabItem(
    isSelected: Boolean,
    icon: ImageVector,
    badgeCount: Int? = null,
    onSelect: () -> Unit
) {
    BottomNavigationItem(
        modifier = Modifier.padding(bottom = getPlatformPadding()),
        selectedContentColor = RedBright,
        unselectedContentColor = RedBrightFaded,
        selected = isSelected,
        onClick = onSelect,
        icon = {
            if (badgeCount != null) {
                Box(contentAlignment = Alignment.TopEnd) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")

                    if (badgeCount > 0) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .offset(x = (8).dp, y = (-8).dp)
                                .background(RedBright, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.offset(y = (-2).dp),
                                text = badgeCount.toString(),
                                textAlign = TextAlign.Center,
                                color = White,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            } else {
                Icon(icon, contentDescription = "Icon")
            }
        }
    )
}

fun Modifier.synthwaveGrid(selectedTab: Int): Modifier = this
    .background(
        Brush.verticalGradient(
            if (selectedTab == 4) listOf(Pink2, Blue2) else listOf(PinkNeon, PurpleDark)
        )
    )
    .drawBehind {
        // Draw grid lines over the background
        drawPath(
            path = Path().apply {
                for (i in 0..size.width.toInt() step 40) {
                    moveTo(i.toFloat(), 0f)
                    lineTo(i.toFloat(), size.height)
                }
                for (i in 0..size.height.toInt() step 40) {
                    moveTo(0f, i.toFloat())
                    lineTo(size.width, i.toFloat())
                }
            },
            color = Color(0xFFEE00FF).copy(alpha = 0.2f), // Neon pink grid
            style = Stroke(width = 1.dp.toPx())
        )
    }
