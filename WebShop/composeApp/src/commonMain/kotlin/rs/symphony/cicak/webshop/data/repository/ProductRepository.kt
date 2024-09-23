package rs.symphony.cicak.webshop.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import rs.symphony.cicak.webshop.domain.Product

interface ProductRepository {
    fun initializeProducts()
    fun toggleFavorite(productId: Long)
    fun getProducts(): StateFlow<List<Product>>
    fun getFavoriteProducts(): List<Product>
}

class ProductRepositoryFake() : ProductRepository {

    // Internal product list with StateFlow to observe changes
    private val _products = MutableStateFlow<List<Product>>(emptyList())

    init {
        initializeProducts()
    }

    override fun getProducts(): StateFlow<List<Product>> = _products

    // Initialize the repository with some fake products
    override fun initializeProducts() {
        val productList = List(100) {
            Product(
                it.toLong(), "Product $it", it.toDouble() * 10,
                favorite = it.toLong() % 6 == 0.toLong()
            )
        }
        _products.value = productList
    }

    // Toggle favorite status of a product
    override fun toggleFavorite(productId: Long) {
        _products.value = _products.value.map { product ->
            if (product.id == productId) product.copy(favorite = !product.favorite)
            else product
        }
    }

    // Return only favorite products
    override fun getFavoriteProducts(): List<Product> {
        return _products.value.filter { it.favorite }
    }
}
