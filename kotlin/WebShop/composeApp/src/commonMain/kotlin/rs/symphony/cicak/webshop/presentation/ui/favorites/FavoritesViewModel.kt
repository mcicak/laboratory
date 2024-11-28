package rs.symphony.cicak.webshop.presentation.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rs.symphony.cicak.webshop.data.repository.CartRepository
import rs.symphony.cicak.webshop.data.repository.ProductRepository
import rs.symphony.cicak.webshop.data.repository.UserRepository
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.domain.ProductId

class FavoritesViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Product>>(emptyList())
    val favorites: StateFlow<List<Product>> = _favorites

    private val _isGridView = MutableStateFlow(true) // Initial value of isGridView
    val isGridView: StateFlow<Boolean> = _isGridView

    init {
        viewModelScope.launch {
            productRepository.getFavoriteProducts().collect {
                _favorites.value = it
            }
        }
    }

    // Toggle favorite status in repository
    fun toggleFavorite(productId: ProductId) {
        viewModelScope.launch {
            userRepository.toggleFavorite(productId)
        }
    }

    fun toggleGridView() {
        _isGridView.value = !_isGridView.value
    }

    fun addToCart(id: ProductId) {
        viewModelScope.launch {
            cartRepository.addToCart(id)
        }
    }
}