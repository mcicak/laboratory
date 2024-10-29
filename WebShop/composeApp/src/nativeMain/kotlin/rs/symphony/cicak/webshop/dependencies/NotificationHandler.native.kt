package rs.symphony.cicak.webshop.dependencies

import rs.symphony.cicak.webshop.domain.ProductId

actual class NotificationHandler : INotificationHandler {

    override fun showProductDetails(productId: ProductId) {
        // iOS push notification handled in iOSApp.swift
    }
}