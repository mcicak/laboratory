package rs.symphony.cicak.webshop.presentation.ui.main

import kotlinx.serialization.Serializable
import rs.symphony.cicak.webshop.domain.ProductId

@Serializable
object HomeDestination

@Serializable
data class ProductDetailsDestination(
    val id: ProductId
)
