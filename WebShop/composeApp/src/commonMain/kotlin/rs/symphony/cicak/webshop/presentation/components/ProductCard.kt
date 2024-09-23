package rs.symphony.cicak.webshop.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.domain.getImageResource

@Composable
fun ProductCard(item: Product, onFavoriteToggle: (Long) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            //.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Placeholder image
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .aspectRatio(1f), // Ensures the image is square
                painter = painterResource(item.getImageResource()),
                contentScale = ContentScale.Fit,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Product name
            Text(
                text = item.name,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Price and Favorite icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${item.price}$",
                    style = MaterialTheme.typography.body1
                )

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable(
                            onClick = { onFavoriteToggle(item.id) },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                ) {
                    Icon(
                        imageVector = if (item.favorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (item.favorite) Color.Red else Color.Gray
                    )
                }

            }
        }
    }
}
