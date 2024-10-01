package rs.symphony.cicak.webshop.presentation.ui.main

import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Serializable
data class ProductDetailsDestination(
    val id: Long
)
