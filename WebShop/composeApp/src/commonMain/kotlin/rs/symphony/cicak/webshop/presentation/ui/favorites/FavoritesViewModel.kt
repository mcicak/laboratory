package rs.symphony.cicak.webshop.presentation.ui.favorites

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import rs.symphony.cicak.webshop.data.repository.ProductRepository
import rs.symphony.cicak.webshop.domain.Product

class FavoritesViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Product>>(emptyList())
    val favorites: StateFlow<List<Product>> = _favorites

    private val _isGridView = MutableStateFlow(true) // Initial value of isGridView
    val isGridView: StateFlow<Boolean> = _isGridView

    fun fetchFavorites() {
        _favorites.value = productRepository.getFavoriteProducts()
    }

    // Toggle favorite status in repository
    fun toggleFavorite(productId: Long) {
        productRepository.toggleFavorite(productId)
        fetchFavorites() // Refresh the favorite list
    }

    fun toggleGridView() {
        _isGridView.value = !_isGridView.value
    }
}