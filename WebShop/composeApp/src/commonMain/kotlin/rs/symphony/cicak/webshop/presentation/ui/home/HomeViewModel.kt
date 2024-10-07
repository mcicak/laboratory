package rs.symphony.cicak.webshop.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rs.symphony.cicak.webshop.data.repository.CartRepository
import rs.symphony.cicak.webshop.data.repository.ProductRepository
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.domain.ProductId

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Success(val products: List<Product>) : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
}

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val screenState: StateFlow<HomeScreenState> = _screenState

    fun fetchHomeProducts() {
        viewModelScope.launch {

            try {
                productRepository.getProducts().collect { products ->
                    if (products.isEmpty()) {
                        _screenState.value = HomeScreenState.Loading
                    } else {
                        _screenState.value = HomeScreenState.Success(products)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _screenState.value = HomeScreenState.Error("Failed to load products")
            }
        }
    }

    fun toggleFavorite(productId: ProductId) {
        viewModelScope.launch {
            productRepository.toggleFavorite(productId)
        }
    }

    fun addToCart(id: ProductId) {
        viewModelScope.launch {
            cartRepository.addToCart(id)
        }
    }
}