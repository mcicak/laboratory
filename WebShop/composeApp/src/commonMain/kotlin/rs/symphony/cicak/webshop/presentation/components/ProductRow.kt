package rs.symphony.cicak.webshop.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.domain.ProductId
import rs.symphony.cicak.webshop.presentation.ui.main.Cyan
import rs.symphony.cicak.webshop.presentation.ui.main.Cyan2
import rs.symphony.cicak.webshop.presentation.ui.main.Purple2

@Composable
fun ProductRow(item: Product, isFavorite: Boolean, onFavoriteToggle: (ProductId) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Placeholder image

        KamelImage(
            modifier = Modifier
                .size(64.dp)
                .padding(end = 8.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Cyan2, Purple2),
                        center = Offset.Unspecified,
                        radius = Float.POSITIVE_INFINITY
                    )
                ),
            resource = asyncPainterResource(item.images.first()),
            contentDescription = null
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, style = MaterialTheme.typography.h6, color = Cyan)
            Text(text = "${item.price}$", style = MaterialTheme.typography.body1, color = Cyan)
        }

        IconButton(onClick = { onFavoriteToggle(item.id) }) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = Cyan
            )
        }
    }
}