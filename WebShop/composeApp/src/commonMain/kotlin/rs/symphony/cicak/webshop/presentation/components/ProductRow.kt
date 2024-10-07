package rs.symphony.cicak.webshop.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.domain.ProductId

@Composable
fun ProductRow(item: Product, onFavoriteToggle: (ProductId) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Placeholder image

        KamelImage(
            modifier = Modifier
                .size(64.dp)
                .padding(end = 8.dp),
            resource = asyncPainterResource(item.images.first()),
            contentDescription = null
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, style = MaterialTheme.typography.h6)
            Text(text = "${item.price}$", style = MaterialTheme.typography.body1)
        }

        IconButton(onClick = { onFavoriteToggle(item.id) }) {
            Icon(
                imageVector = if (item.isFavorite(emptyList())) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = if (item.isFavorite(emptyList())) Color.Red else Color.Gray
            )
        }
    }
}