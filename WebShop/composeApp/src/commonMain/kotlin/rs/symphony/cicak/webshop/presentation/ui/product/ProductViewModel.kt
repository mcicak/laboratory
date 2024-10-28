package rs.symphony.cicak.webshop.presentation.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import rs.symphony.cicak.webshop.data.repository.CartRepository
import rs.symphony.cicak.webshop.data.repository.ProductRepository
import rs.symphony.cicak.webshop.data.repository.UserRepository
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.domain.ProductId

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val productId: ProductId
) : ViewModel() {

    val favorites: Flow<List<Product>> = productRepository.getFavoriteProducts()

    private val _recommendedProducts = MutableStateFlow<List<Product>>(emptyList())
    val recommendedProducts: StateFlow<List<Product>> = _recommendedProducts

    private fun loadProductDetails(productId: Long) {
        // Logic to load product details based on productId
    }

    fun isFavorite(productId: String): Flow<Boolean> {
        return favorites.map { favoriteList ->
            favoriteList.any { it.id == productId }
        }
    }

    fun toggleFavorite(productId: ProductId) {
        viewModelScope.launch {
            userRepository.toggleFavorite(productId)
        }
    }

    fun addToCart(id: ProductId) {
        viewModelScope.launch {
            cartRepository.addToCart(id)
        }
    }

    fun getProduct(productId: ProductId): Product {
        return productRepository.getProduct(productId)
    }

    fun loadRecommendedProducts() {
        viewModelScope.launch {
            val product = productRepository.getProduct(productId)
            productRepository.getRecommendedProducts(productId, product.category)
                .collect { recommended ->
                    _recommendedProducts.value = recommended
                }
        }
    }
}
