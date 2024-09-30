package rs.symphony.cicak.webshop.presentation.ui.main

import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
data class ProductDetailsScreen(
    val id: Long
)
