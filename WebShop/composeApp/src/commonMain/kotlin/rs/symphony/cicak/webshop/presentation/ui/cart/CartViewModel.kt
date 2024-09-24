package rs.symphony.cicak.webshop.presentation.ui.cart

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import rs.symphony.cicak.webshop.data.repository.CartRepository
import rs.symphony.cicak.webshop.domain.CartItem

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {

    val cartItems: StateFlow<List<CartItem>> = cartRepository.getCartItems()

    val totalCost: StateFlow<Double> = cartRepository.calculateTotalCost()

    fun addToCart(productId: Long) {
        cartRepository.addToCart(productId)
    }

    fun removeFromCart(productId: Long) {
        cartRepository.removeFromCart(productId)
    }

    fun updateCartItemQuantity(productId: Long, quantity: Int) {
        cartRepository.updateCartItemQuantity(productId, quantity)
    }
}
