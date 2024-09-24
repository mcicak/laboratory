package rs.symphony.cicak.webshop.data.repository

import kotlinx.coroutines.flow.StateFlow
import rs.symphony.cicak.webshop.domain.Product

interface ProductRepository {
    fun toggleFavorite(productId: Long)
    fun getProducts(): StateFlow<List<Product>>
    fun getFavoriteProducts(): List<Product>
}

class ProductRepositoryFake(
    private val appModel: AppModel
) : ProductRepository {

    // Internal product list with StateFlow to observe changes
    //private val _products = MutableStateFlow<List<Product>>(emptyList())
    override fun getProducts(): StateFlow<List<Product>> = appModel.products

    // Toggle favorite status of a product
    override fun toggleFavorite(productId: Long) {
        appModel.updateProducts(
            appModel.products.value.map { product ->
                if (product.id == productId) product.copy(favorite = !product.favorite)
                else product
            }
        )
    }

    // Return only favorite products
    override fun getFavoriteProducts(): List<Product> {
        return appModel.products.value.filter { it.favorite }
    }
}
