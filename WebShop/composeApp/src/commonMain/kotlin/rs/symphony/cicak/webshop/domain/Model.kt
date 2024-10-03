package rs.symphony.cicak.webshop.domain

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import webshop.composeapp.generated.resources.Res
import webshop.composeapp.generated.resources.p1
import webshop.composeapp.generated.resources.p2
import webshop.composeapp.generated.resources.p3
import webshop.composeapp.generated.resources.p4
import webshop.composeapp.generated.resources.p5

typealias ProductId = Long

data class User(
    val name: String,
    val email: String
)

data class Product(
    val id: ProductId,
    val title: String,
    val subtitle: String,
    val price: Double,
    val favorite: Boolean = false
)

fun Product.getImageResource(): DrawableResource {
    return when (id % 5) {
        0L -> Res.drawable.p1
        1L -> Res.drawable.p2
        2L -> Res.drawable.p3
        3L -> Res.drawable.p4
        else -> Res.drawable.p5
    }
}

data class ProductDetails(
    val id: ProductId,
    val title: String,
    val subtitle: String,
    val description: String,
    val images: List<DrawableResource>, // convert to URLs
    val price: Double,
    val currency: String
)

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
