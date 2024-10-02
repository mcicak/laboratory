package rs.symphony.cicak.webshop.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import rs.symphony.cicak.webshop.domain.CartItem
import rs.symphony.cicak.webshop.domain.Category
import rs.symphony.cicak.webshop.domain.Currency
import rs.symphony.cicak.webshop.domain.Product

class AppModel {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    private val _currency = MutableStateFlow<Currency>(Currency.RSD)

    val categories: StateFlow<List<Category>> = _categories
    val products: StateFlow<List<Product>> = _products
    val cartItems: StateFlow<List<CartItem>> = _cartItems
    val currency: StateFlow<Currency> = _currency

    init {
        initializeProducts()
    }

    fun initializeProducts() {
        val productList = List(100) {
            Product(
                it.toLong(), "Product $it", "", (it.toDouble() + 10) * 10,
                favorite = it.toLong() % 6 == 0.toLong()
            )
        }
        _products.value = productList
    }

    fun updateCategories(newCategories: List<Category>) {
        _categories.value = newCategories
    }

    fun updateProducts(newProducts: List<Product>) {
        _products.value = newProducts
    }

    fun updateCartItems(newCartItems: List<CartItem>) {
        _cartItems.value = newCartItems
    }

    fun getProductById(id: Long): Product? {
        return _products.value.find { it.id == id }
    }
}
