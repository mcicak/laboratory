package rs.symphony.cicak.webshop.domain

data class Product(
    val id: Long,
    val name: String,
    val price: Double
)

data class Category(
    val id: Long,
    val name: String,
    val price: Double
)
