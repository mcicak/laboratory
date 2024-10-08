package rs.symphony.cicak.webshop.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import rs.symphony.cicak.webshop.data.repository.CartRepository
import rs.symphony.cicak.webshop.data.repository.ProductRepository
import rs.symphony.cicak.webshop.data.repository.UserRepository
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.domain.ProductId

data class HomeScreenUi(
    val products: List<Product>,
    val favorites: List<ProductId>
)

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Success(val model: HomeScreenUi) : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
}

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val screenState: StateFlow<HomeScreenState> = _screenState

    fun fetchHomeProducts() {
        viewModelScope.launch {

            try {
                val productFlow = productRepository.getProducts()
                val userFlow = userRepository.user

                combine(productFlow, userFlow) { products, user ->
                    if (products.isEmpty()) {
                        HomeScreenState.Loading
                    } else {
                        HomeScreenState.Success(
                            HomeScreenUi(
                                products = products,
                                favorites = user?.favorite ?: emptyList()
                            )
                        )
                    }
                }.collect { screenState ->
                    _screenState.value = screenState
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _screenState.value = HomeScreenState.Error("Failed to load products")
            }
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
}