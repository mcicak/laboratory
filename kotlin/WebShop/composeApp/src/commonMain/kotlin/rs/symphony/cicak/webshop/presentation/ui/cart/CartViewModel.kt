package rs.symphony.cicak.webshop.presentation.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import rs.symphony.cicak.webshop.data.repository.CartRepository
import rs.symphony.cicak.webshop.data.repository.ProductRepository
import rs.symphony.cicak.webshop.domain.Currency
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.domain.ProductId

data class CartItemUi(
    val product: Product,
    val quantity: Int
)

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    //val cartItems: StateFlow<List<CartItem>> = cartRepository.getCartItems()

    private val _cartItemsUi = MutableStateFlow<List<CartItemUi>>(emptyList())
    val cartItemsUi: StateFlow<List<CartItemUi>> = _cartItemsUi

    init {
        viewModelScope.launch {
            combine(
                cartRepository.getCartItems(),
                productRepository.getProducts()
            ) { cartItems, products ->
                cartItems.mapNotNull { cartItem ->
                    val product = products.find { it.id == cartItem.productId }
                    product?.let { CartItemUi(it, cartItem.quantity) }
                }
            }.collect { _cartItemsUi.value = it }
        }
    }

    //val favorites: Flow<List<CartItemUi>> = cartRepository.getCartItems()

    val totalCost: Flow<Double> = cartRepository.calculateTotalCost()
    val currency: Currency = cartRepository.getCurrency()

    val totalCartItemCount: StateFlow<Int> = cartItemsUi.map { items ->
        items.sumOf { it.quantity }  // Sum the quantities of all cart items
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    fun addToCart(productId: ProductId) {
        viewModelScope.launch {
            cartRepository.addToCart(productId)
        }
    }

    fun removeFromCart(productId: ProductId) {
        viewModelScope.launch {
            cartRepository.removeFromCart(productId)
        }
    }

    fun updateCartItemQuantity(productId: ProductId, quantity: Int) {
        viewModelScope.launch {
            cartRepository.updateCartItemQuantity(productId, quantity)
        }
    }

    fun increaseQuantity(cartItem: CartItemUi) {
        viewModelScope.launch {
            cartRepository.updateCartItemQuantity(cartItem.product.id, cartItem.quantity + 1)
        }
    }

    fun decreaseQuantity(cartItem: CartItemUi) {
        viewModelScope.launch {
            cartRepository.updateCartItemQuantity(cartItem.product.id, cartItem.quantity - 1)
        }
    }
}
