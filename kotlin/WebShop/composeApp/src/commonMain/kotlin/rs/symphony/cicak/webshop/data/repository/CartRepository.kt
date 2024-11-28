package rs.symphony.cicak.webshop.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import rs.symphony.cicak.webshop.domain.CartItem
import rs.symphony.cicak.webshop.domain.Currency
import rs.symphony.cicak.webshop.domain.ProductId

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun addToCart(productId: ProductId)
    suspend fun removeFromCart(productId: ProductId)
    suspend fun updateCartItemQuantity(productId: ProductId, quantity: Int)
    fun calculateTotalCost(): Flow<Double>
    fun getCurrency(): Currency
}

class CartRepositoryImpl(
    private val productRepository: ProductRepository,
    private val appModel: AppModel
) : CartRepository {

    private val db = Firebase.firestore

    override fun getCartItems(): Flow<List<CartItem>> = flow {
        appModel.user.value?.let { user ->
            try {
                db.collection("users").document(user.id).collection("cart").snapshots
                    .collect { snapshot ->
                        val cartItems = snapshot.documents.map { doc ->
                            doc.data<CartItem>()
                        }
                        emit(cartItems)
                    }
            } catch (e: Exception) {
                emit(emptyList())
            }
        }
    }

    override suspend fun addToCart(productId: ProductId) {
        appModel.user.value?.let { user ->
            val cartItemsCollection = db.collection("users")
                .document(user.id).collection("cart")

            try {
                val cartItemsSnapshot = cartItemsCollection.where { "productId" equalTo productId }.get()

                if (cartItemsSnapshot.documents.isNotEmpty()) {
                    // If the product already exists in the cart, increment its quantity
                    val cartItemDoc = cartItemsSnapshot.documents.first()
                    val cartItem = cartItemDoc.data<CartItem>()

                    cartItem.let {
                        if (it.quantity < 5) {
                            cartItemDoc.reference.update("quantity" to it.quantity + 1)
                        }
                    }
                } else {
                    // If the product doesn't exist in the cart, add it
                    val newCartItem = hashMapOf(
                        "productId" to productId,
                        "quantity" to 1
                    )
                    cartItemsCollection.add(newCartItem)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun removeFromCart(productId: ProductId) {
        appModel.user.value?.let { user ->
            val cartItemsCollection = db.collection("users").document(user.id).collection("cart")

            try {
                val cartItemsSnapshot = cartItemsCollection.where { "productId" equalTo productId }.get()
                if (cartItemsSnapshot.documents.isNotEmpty()) {
                    val cartItemDoc = cartItemsSnapshot.documents.first()
                    cartItemDoc.reference.delete()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun updateCartItemQuantity(productId: ProductId, quantity: Int) {
        appModel.user.value?.let { user ->
            val cartItemsCollection = db.collection("users").document(user.id).collection("cart")

            try {
                val cartItemsSnapshot = cartItemsCollection.where { "productId" equalTo productId }.get()

                if (cartItemsSnapshot.documents.isNotEmpty()) {
                    val cartItemDoc = cartItemsSnapshot.documents.first()

                    if (quantity in 1..5) {
                        cartItemDoc.reference.update("quantity" to quantity)
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun calculateTotalCost(): Flow<Double> {
        val cartItemsFlow = getCartItems()
        val productsFlow = productRepository.getProducts()

        return combine(cartItemsFlow, productsFlow) { cartItems, products ->
            var total = 0.0
            cartItems.forEach { cartItem ->
                val product = products.find { it.id == cartItem.productId }
                product?.let {
                    total += cartItem.quantity * it.price // Assuming product has a 'price' field
                }
            }
            total
        }
    }

    override fun getCurrency(): Currency {
        return Currency.USD
    }
}
