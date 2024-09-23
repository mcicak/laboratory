package rs.symphony.cicak.webshop.domain

import org.jetbrains.compose.resources.DrawableResource
import webshop.composeapp.generated.resources.*

data class Product(
    val id: Long,
    val name: String,
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

data class Category(
    val id: Long,
    val name: String,
    val price: Double
)
