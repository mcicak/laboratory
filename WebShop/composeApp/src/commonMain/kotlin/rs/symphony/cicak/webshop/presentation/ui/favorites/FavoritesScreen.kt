package rs.symphony.cicak.webshop.presentation.ui.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.presentation.components.ProductCard
import rs.symphony.cicak.webshop.presentation.components.ProductRow

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel) {

    val favorites by viewModel.favorites.collectAsState(initial = emptyList())
    val isGridView by viewModel.isGridView.collectAsState()

    Scaffold(
        topBar = { FavoritesTopBar(isGridView) { viewModel.toggleGridView() } }
    ) { padding ->
        if (favorites.isEmpty()) {
            EmptyFavouritesView(padding)
        } else {
            if (isGridView) {
                FavoritesGridView(padding, favorites, viewModel)
            } else {
                FavoritesListView(padding, favorites, viewModel)
            }
        }
    }
}

@Composable
fun FavoritesListView(
    padding: PaddingValues,
    favorites: List<Product>,
    viewModel: FavoritesViewModel
) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
        items(favorites.size) { idx ->
            ProductRow(
                item = favorites[idx],
                isFavorite = true,
                onFavoriteToggle = { productId ->
                    viewModel.toggleFavorite(productId)
                }
            )
        }
    }
}

@Composable
fun FavoritesGridView(
    padding: PaddingValues,
    favorites: List<Product>,
    viewModel: FavoritesViewModel
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().padding(padding),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(favorites.size) { index ->
            ProductCard(item = favorites[index],
                isFavorite = true,
                onFavoriteToggle = {
                    viewModel.toggleFavorite(favorites[index].id)
                },
                onAddToCart = {
                    viewModel.addToCart(favorites[index].id)
                }
            )
        }
    }
}

@Composable
private fun FavoritesTopBar(isGridView: Boolean, onToggle: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Favourites",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )

        IconButton(onClick = onToggle) {
            Icon(
                imageVector = if (isGridView) Icons.Default.GridView else
                    Icons.AutoMirrored.Filled.ViewList,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun EmptyFavouritesView(padding: PaddingValues) {
    val lightGrayColor = Color.LightGray

    Column(
        modifier = Modifier.fillMaxSize().padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(160.dp),
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            colorFilter = ColorFilter.tint(lightGrayColor)
        )
        Text(
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
            text = "No Favorites",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = lightGrayColor
        )
        Text(
            text = "Mark an item as favourite by clicking\non the heart symbol.",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = lightGrayColor
        )
    }
}
