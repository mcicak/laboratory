package rs.symphony.cicak.webshop.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.domain.ProductId

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    fun getCategoryProducts(categoryId: String): Flow<List<Product>>
    fun getFavoriteProducts(): Flow<List<Product>>
    fun getProduct(productId: ProductId): Product
    fun getRecommendedProducts(productId: ProductId, categoryId: String): Flow<List<Product>>
}

class ProductRepositoryImpl(
    private val appModel: AppModel
) : ProductRepository {

    private val firestore = Firebase.firestore

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

    override fun getCategoryProducts(categoryId: String) = flow {
        firestore.collection("products")
            .where { "category" equalTo categoryId }
            .snapshots.collect { snapshot ->
                val products = snapshot.documents.map { doc ->
                    doc.data<Product>()
                }.sortedBy { it.title }
                appModel.updateProducts(products)
                emit(products)
            }
    }

    override fun getFavoriteProducts(): Flow<List<Product>> = flow {
        combine(getProducts(), appModel.user) { products, user ->
            // If user is not null, filter products that are in the user's favorites list
            user?.let {
                products.filter { product ->
                    user.favorite.contains(product.id)
                }
            } ?: emptyList()
        }.collect {
            emit(it)  // Emit the filtered list of favorite products
        }
    }

    override fun getProduct(productId: ProductId): Product {
        return appModel.products.value.find { it.id == productId } ?: Product.empty()
    }

    override fun getRecommendedProducts(productId: ProductId, categoryId: String) = flow {
        firestore.collection("products")
            .where { "category" equalTo categoryId }
            .where { "__name__" notEqualTo productId }
            .limit(10)
            .snapshots.collect { snapshot ->
                val products = snapshot.documents.shuffled().take(2).map { doc ->
                    doc.data<Product>()
                }.sortedBy { it.title }
                emit(products)
            }
    }
}
