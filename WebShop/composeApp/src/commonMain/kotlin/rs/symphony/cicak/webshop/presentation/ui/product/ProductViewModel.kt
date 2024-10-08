package rs.symphony.cicak.webshop.presentation.ui.product

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

sealed class ProductScreenState {
    object Loading : ProductScreenState()
    data class Success(val products: List<Product>) : ProductScreenState()
    data class Error(val message: String) : ProductScreenState()
}

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val productId: ProductId
) : ViewModel() {

    private val _productScreenState = MutableStateFlow<ProductScreenState>(ProductScreenState.Loading)
    val productScreenState: StateFlow<ProductScreenState> = _productScreenState

    private fun loadProductDetails(productId: Long) {
        // Logic to load product details based on productId
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
}
