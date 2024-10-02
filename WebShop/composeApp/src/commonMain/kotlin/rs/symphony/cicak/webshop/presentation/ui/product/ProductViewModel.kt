package rs.symphony.cicak.webshop.presentation.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rs.symphony.cicak.webshop.data.repository.CartRepository
import rs.symphony.cicak.webshop.data.repository.ProductRepository
import rs.symphony.cicak.webshop.domain.Currency
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.domain.ProductDetails
import rs.symphony.cicak.webshop.domain.ProductId
import rs.symphony.cicak.webshop.domain.getImageResource

sealed class ProductScreenState {
    object Loading : ProductScreenState()
    data class Success(val products: List<Product>) : ProductScreenState()
    data class Error(val message: String) : ProductScreenState()
}

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val productId: ProductId
) : ViewModel() {

    private val _productScreenState = MutableStateFlow<ProductScreenState>(ProductScreenState.Loading)
    val productScreenState: StateFlow<ProductScreenState> = _productScreenState

    private fun loadProductDetails(productId: Long) {
        // Logic to load product details based on productId
    }

    fun toggleFavorite(productId: Long) {
        viewModelScope.launch {
            productRepository.toggleFavorite(productId)
        }
    }

    fun addToCart(id: Long) {
        viewModelScope.launch {
            cartRepository.addToCart(id)
        }
    }

    fun getProduct(productId: ProductId): ProductDetails {
        val thumb = productRepository.getProducts().value.first { it.id == productId }
        val ret = ProductDetails(
            id = thumb.id,
            title = thumb.title,
            subtitle = thumb.subtitle,
            price = thumb.price,
            currency = Currency.USD.symbol,
            description = "",
            images = listOf(thumb.getImageResource()),
        )
        return ret
    }
}
