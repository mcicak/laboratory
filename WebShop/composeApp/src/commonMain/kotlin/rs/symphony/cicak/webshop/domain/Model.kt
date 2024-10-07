package rs.symphony.cicak.webshop.domain

import kotlinx.serialization.Serializable

typealias ProductId = String

data class User(
    val name: String,
    val email: String
)

@Serializable
data class Product(
    val id: ProductId,
    val title: String,
    val subtitle: String,
    val description: String,
    val images: List<String>,
    val price: Double,
    val currency: String
) {
    fun isFavorite(userFavorites: List<ProductId>): Boolean {
        return userFavorites.contains(this.id)
    }

    companion object {
        fun empty(): Product {
            return Product(
                id = "",
                title = "",
                subtitle = "",
                description = "",
                images = emptyList(),
                price = 0.0,
                currency = ""
            )
        }
    }
}

@Serializable
data class Category(
    val id: String,
    val name: String,
    val image: String,
    val order: Int
)

data class CartItem(
    val productId: ProductId,
    val quantity: Int
)

data class Currency(
    val name: String,
    val symbol: String
) {
    companion object {
        val USD = Currency("US Dollar", "USD")
        val EUR = Currency("EURO", "EUR")
        val RSD = Currency("Serbian Dinar", "RSD")
    }
}
