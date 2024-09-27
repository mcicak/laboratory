package rs.symphony.cicak.webshop.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import rs.symphony.cicak.webshop.presentation.ui.cart.CartScreen
import rs.symphony.cicak.webshop.presentation.ui.cart.CartViewModel
import rs.symphony.cicak.webshop.presentation.ui.categories.CategoriesScreen
import rs.symphony.cicak.webshop.presentation.ui.categories.CategoriesViewModel
import rs.symphony.cicak.webshop.presentation.ui.favorites.FavoritesScreen
import rs.symphony.cicak.webshop.presentation.ui.favorites.FavoritesViewModel
import rs.symphony.cicak.webshop.presentation.ui.home.HomeScreen
import rs.symphony.cicak.webshop.presentation.ui.home.HomeViewModel
import rs.symphony.cicak.webshop.presentation.ui.profile.ProfileScreen

@OptIn(KoinExperimentalAPI::class)
@Composable
fun WebShopApp() {
    var selectedTab by remember { mutableStateOf(0) }

    val homeViewModel = koinViewModel<HomeViewModel>()
    val categoriesViewModel = koinViewModel<CategoriesViewModel>()
    val favoritesViewModel = koinViewModel<FavoritesViewModel>()
    val cartViewModel = koinViewModel<CartViewModel>()

    val totalCartItemCount by cartViewModel.totalCartItemCount.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigation(backgroundColor = Color.White) {
                BottomNavigationItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
//                    label = { Text("Home") }
                )
                BottomNavigationItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.List,
                            contentDescription = null
                        )
                    },
//                    label = {
//                        BoxWithConstraints {
//                            Text(
//                                modifier = Modifier
//                                    .wrapContentWidth(unbounded = true)
//                                    .requiredWidth(maxWidth + 24.dp),
//                                text = "Categories",
//                                maxLines = 1,
//                                softWrap = false,
//                            )
//                        }
//                    }
                )
                BottomNavigationItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
//                    label = { Text("Favorites") }
                )
                BottomNavigationItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = {
                        Box(
                            contentAlignment = Alignment.TopEnd
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")

                            if (totalCartItemCount > 0) {
                                // Place the badge using padding to control its placement
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .offset(x = (8).dp, y = (-8).dp)
                                        .background(Color.Red, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        modifier = Modifier.offset(y = (-2).dp),
                                        text = totalCartItemCount.toString(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                )
                BottomNavigationItem(
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
//                    label = { Text("Profile") }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (selectedTab) {
                0 -> HomeScreen(homeViewModel)
                1 -> CategoriesScreen(categoriesViewModel)
                2 -> FavoritesScreen(favoritesViewModel)
                3 -> CartScreen(cartViewModel)
                4 -> ProfileScreen()
            }
        }
    }
}
