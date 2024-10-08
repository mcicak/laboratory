package rs.symphony.cicak.webshop.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.domain.ProductId

interface ProductRepository {
    fun toggleFavorite(productId: ProductId)
    fun getProducts(): Flow<List<Product>>
    fun getFavoriteProducts(): List<Product>
    fun getProduct(productId: ProductId): Product
}

class ProductRepositoryImpl(
    private val appModel: AppModel
) : ProductRepository {

    private val firestore = Firebase.firestore

    override fun toggleFavorite(productId: ProductId) {
        appModel.setUser(
            appModel.user.value?.copy(
                favorite = appModel.user.value?.favorite?.plus(productId) ?: listOf(
                    productId
                )
            )
        )

        // update in firestore

        // if firestore update failed, remove favorite item from appModel.user to rollback the state
    }

    override fun getProducts() = flow {
        firestore.collection("products")
            .snapshots.collect { snapshot ->
                val products = snapshot.documents.map { doc ->
                    doc.data<Product>()
                }.sortedBy { it.title }
                appModel.updateProducts(products)
                emit(products)
            }
    }

    override fun getFavoriteProducts(): List<Product> {
        TODO("Not yet implemented")
    }

    override fun getProduct(productId: ProductId): Product {
        return appModel.products.value.find { it.id == productId } ?: Product.empty()
    }
}

class ProductRepositoryFake(
    private val appModel: AppModel
) : ProductRepository {

    // Internal product list with StateFlow to observe changes
    //private val _products = MutableStateFlow<List<Product>>(emptyList())
    override fun getProducts(): StateFlow<List<Product>> = appModel.products

    // Toggle favorite status of a product
    override fun toggleFavorite(productId: ProductId) {
//        appModel.updateProducts(
//            appModel.products.value.map { product ->
//                if (product.id == productId) product.copy(favorite = !product.favorite)
//                else product
//            }
//        )
    }

    // Return only favorite products
    override fun getFavoriteProducts(): List<Product> {
        return appModel.products.value.filter { it.isFavorite(emptyList()) }
    }

    override fun getProduct(productId: ProductId): Product {
        return Product.empty()
    }
}
