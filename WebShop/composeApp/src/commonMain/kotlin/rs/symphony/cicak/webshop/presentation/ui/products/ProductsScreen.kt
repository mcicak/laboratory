package rs.symphony.cicak.webshop.presentation.ui.products

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import rs.symphony.cicak.webshop.domain.ProductId
import rs.symphony.cicak.webshop.presentation.components.ProductCard
import rs.symphony.cicak.webshop.presentation.components.Title
import rs.symphony.cicak.webshop.presentation.ui.main.Transparent
import rs.symphony.cicak.webshop.presentation.util.getPlatformPadding

@Composable
fun ProductsScreen(
    viewModel: HomeViewModel,
    categoryId: String? = null,
    onProductClick: (ProductId) -> Unit,
    onBack: (() -> Unit)? = null
) {
    val state by viewModel.screenState.collectAsState()

    LaunchedEffect(true) {
        if (categoryId != null) {
            viewModel.fetchCategoryProducts(categoryId)
        } else {
            viewModel.fetchHomeProducts()
        }
    }

    Scaffold(
        backgroundColor = Transparent,
        topBar = {
            if (categoryId != null) {
                Column {
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp, top = 16.dp + getPlatformPadding())
                            .clip(RoundedCornerShape(50))
                            .background(Color.White)
                            .size(40.dp)
                            .clickable(onClick = { onBack?.invoke() })
                    ) {
                        Icon(
                            modifier = Modifier.align(alignment = Alignment.Center),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }

                    Title(
                        modifier = Modifier.padding(16.dp, top = 16.dp + getPlatformPadding()),
                        text = viewModel.title(categoryId)
                    )
                }
            } else {
                Title(
                    modifier = Modifier.padding(16.dp, top = 16.dp + getPlatformPadding()),
                    text = viewModel.title(categoryId)
                )
            }
        }
    ) {
        when (state) {
            is HomeScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        color = Color.Magenta // Neon-style progress indicator
                    )
                }
            }

            is HomeScreenState.Success -> {
                val successState = (state as HomeScreenState.Success)
                val products = remember { successState.model.products }
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(30.dp),
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
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .padding(8.dp)
                        )
                    }
                }
            }

            is HomeScreenState.Error -> {
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = "Error fetching data",
                    color = Color.Red // Error text in neon style
                )
            }
        }
    }
}
