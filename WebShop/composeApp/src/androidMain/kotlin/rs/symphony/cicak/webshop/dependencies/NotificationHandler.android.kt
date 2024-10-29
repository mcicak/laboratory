package rs.symphony.cicak.webshop.dependencies

import android.content.Context
import android.content.Intent
import rs.symphony.cicak.webshop.ProductDetailsActivity
import rs.symphony.cicak.webshop.domain.ProductId

actual class NotificationHandler(
    private val context: Context
) : INotificationHandler {

    override fun showProductDetails(productId: ProductId) {

        val intent = Intent(context, ProductDetailsActivity::class.java).apply {
            putExtra("productId", productId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        context.startActivity(intent)
    }
}