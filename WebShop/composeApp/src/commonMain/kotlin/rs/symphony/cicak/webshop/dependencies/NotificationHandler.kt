package rs.symphony.cicak.webshop.dependencies

import rs.symphony.cicak.webshop.domain.ProductId

interface INotificationHandler {
    fun showProductDetails(productId: ProductId)
}

expect class NotificationHandler : INotificationHandler
