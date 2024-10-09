package rs.symphony.cicak.webshop.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rs.symphony.cicak.webshop.domain.ProductId
import rs.symphony.cicak.webshop.presentation.components.ProductCard
import rs.symphony.cicak.webshop.presentation.util.getPlatformPadding

@Composable
fun HomeScreen(viewModel: HomeViewModel, onProductClick: (ProductId) -> Unit) {
    val state by viewModel.screenState.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetchHomeProducts()
    }

    Scaffold(
        topBar = {
            Text(
                modifier = Modifier.padding(16.dp, top = 16.dp + getPlatformPadding()),
                text = "Home",
                style = MaterialTheme.typography.h1,
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
                val successState = (state as HomeScreenState.Success)
                val products = remember { successState.model.products }
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {


                    items(products.size, key = { products[it].id }) { index ->
                        ProductCard(
                            item = products[index],
                            isFavorite = products[index].isFavorite(successState.model.favorites),
                            onItemClicked = onProductClick,
                            onFavoriteToggle = {
                                viewModel.toggleFavorite(products[index].id)
                            },
                            onAddToCart = {
                                viewModel.addToCart(products[index].id)
                            })
                    }
                }
            }

            is HomeScreenState.Error -> {
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = "Error fetching data"
                )
            }
        }
    }
}
