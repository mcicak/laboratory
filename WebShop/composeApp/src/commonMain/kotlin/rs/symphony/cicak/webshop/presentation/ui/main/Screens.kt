package rs.symphony.cicak.webshop.presentation.ui.main

import kotlinx.serialization.Serializable
import rs.symphony.cicak.webshop.domain.ProductId

@Serializable
sealed class Destination

@Serializable
object HomeDestination : Destination()

@Serializable
data class ProductsDestination(
    val categoryId: String? = null
) : Destination()

@Serializable
object CategoriesDestination : Destination()

@Serializable
object FavoritesDestination : Destination()

@Serializable
object CartDestination : Destination()

@Serializable
data class ProductDetailsDestination(
    val id: ProductId
) : Destination()
