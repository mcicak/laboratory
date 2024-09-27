package rs.symphony.cicak.webshop.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import rs.symphony.cicak.webshop.domain.CartItem
import rs.symphony.cicak.webshop.domain.Currency

interface CartRepository {
    fun getCartItems(): StateFlow<List<CartItem>>
    fun addToCart(productId: Long)
    fun removeFromCart(productId: Long)
    fun updateCartItemQuantity(productId: Long, quantity: Int)
    fun calculateTotalCost(): StateFlow<Double>
    fun getCurrency(): StateFlow<Currency>
}

class CartRepositoryFake(private val appModel: AppModel) : CartRepository {

    override fun getCartItems(): StateFlow<List<CartItem>> {
        return appModel.cartItems
    }

    override fun addToCart(productId: Long) {
        val currentCart = appModel.cartItems.value.toMutableList()
        val existingItem = currentCart.find { it.productId == productId }

        if (existingItem != null) {
            // Increase quantity if product is already in the cart
            if (existingItem.quantity >= 5) return
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            currentCart[currentCart.indexOf(existingItem)] = updatedItem
        } else {
            // Add new product to cart
            currentCart.add(CartItem(productId, 1))
        }

        appModel.updateCartItems(currentCart)
    }

    override fun removeFromCart(productId: Long) {
        val updatedCart = appModel.cartItems.value.filter { it.productId != productId }
        appModel.updateCartItems(updatedCart)
    }

    override fun updateCartItemQuantity(productId: Long, quantity: Int) {
        if (quantity < 1 || quantity > 5) return

        val updatedCart = appModel.cartItems.value.map { item ->
            if (item.productId == productId) item.copy(quantity = quantity) else item
        }
        appModel.updateCartItems(updatedCart)
    }

    override fun calculateTotalCost(): StateFlow<Double> {
        return appModel.cartItems.map { cartItems ->
            cartItems.sumOf { cartItem ->
                val product = appModel.products.value.find { it.id == cartItem.productId }
                product?.price?.times(cartItem.quantity) ?: 0.0
            }
        }.stateIn(CoroutineScope(Dispatchers.Default), SharingStarted.Eagerly, 0.0)
    }

    override fun getCurrency(): StateFlow<Currency> {
        return appModel.currency
    }
}
