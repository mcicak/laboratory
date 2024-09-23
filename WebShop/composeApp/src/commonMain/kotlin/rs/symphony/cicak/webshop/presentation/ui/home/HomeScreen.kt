package rs.symphony.cicak.webshop.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rs.symphony.cicak.webshop.presentation.components.ProductCard

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state by viewModel.homeScreenState.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetchHomeProducts()
    }

    Scaffold(
        topBar = {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Home",
                fontSize = 32.sp, // Larger font size for prominence
                fontWeight = FontWeight.Bold, // Bold text for prominence
            )
        }
    ) {
        when (state) {
            is HomeScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is HomeScreenState.Success -> {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val products = (state as HomeScreenState.Success).products
                    items(products.size) { index ->
                        ProductCard(item = products[index])
                    }
                }
            }

            is HomeScreenState.Error -> {
                Text("Error fetching data")
            }
        }
    }
}