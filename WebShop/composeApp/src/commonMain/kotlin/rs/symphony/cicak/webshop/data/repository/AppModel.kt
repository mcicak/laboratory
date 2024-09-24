package rs.symphony.cicak.webshop.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import rs.symphony.cicak.webshop.domain.CartItem
import rs.symphony.cicak.webshop.domain.Product

class AppModel {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())

    // Expose the lists as immutable flows to other classes
    val products: StateFlow<List<Product>> = _products
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    init {
        initializeProducts()
    }

    fun initializeProducts() {
        val productList = List(100) {
            Product(
                it.toLong(), "Product $it", (it.toDouble() + 10) * 10,
                favorite = it.toLong() % 6 == 0.toLong()
            )
        }
        _products.value = productList
    }

    // Methods for repositories to update the data
    fun updateProducts(newProducts: List<Product>) {
        _products.value = newProducts
    }

    fun updateCartItems(newCartItems: List<CartItem>) {
        _cartItems.value = newCartItems
    }
}
