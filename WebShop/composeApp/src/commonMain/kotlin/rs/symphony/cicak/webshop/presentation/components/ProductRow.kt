package rs.symphony.cicak.webshop.presentation.components

import androidx.compose.foundation.Image
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
import org.jetbrains.compose.resources.painterResource
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.domain.getImageResource

@Composable
fun ProductRow(item: Product, onFavoriteToggle: (Long) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Placeholder image
        Image(
            painter = painterResource(item.getImageResource()),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .padding(end = 8.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, style = MaterialTheme.typography.h6)
            Text(text = "${item.price}$", style = MaterialTheme.typography.body1)
        }

        IconButton(onClick = { onFavoriteToggle(item.id) }) {
            Icon(
                imageVector = if (item.favorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = if (item.favorite) Color.Red else Color.Gray
            )
        }
    }
}