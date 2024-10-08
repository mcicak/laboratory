package rs.symphony.cicak.webshop.presentation.components

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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.domain.ProductId

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    item: Product,
    isFavorite: Boolean,
    onItemClicked: (ProductId) -> Unit = {},
    onFavoriteToggle: () -> Unit = {},
    onAddToCart: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(1.dp),
        onClick = { onItemClicked(item.id) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                //.padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Placeholder image
                KamelImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .aspectRatio(1f),
                    resource = asyncPainterResource(item.images.first()),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Product name
                Text(
                    text = item.title,
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
                }
            }

            FavoriteButton(Modifier.align(Alignment.TopEnd), item, isFavorite, onFavoriteToggle)
            AddToCartButton(Modifier.align(Alignment.BottomEnd), onAddToCart)
        }
    }
}

@Composable
private fun AddToCartButton(
    modifier: Modifier,
    onAddToCart: () -> Unit
) {
    Box(
        modifier = modifier.padding(8.dp)
            .clickable(
                onClick = onAddToCart,
                indication = null,
                interactionSource = remember { MutableInteractionSource() })
    ) {
        Icon(
            imageVector = Icons.Filled.ShoppingCart,
            contentDescription = null,
            tint = Color.Black
        )
    }
}

@Composable
private fun FavoriteButton(
    modifier: Modifier,
    item: Product,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit,
) {
    Box(
        modifier = modifier.padding(8.dp)
            .clickable(
                onClick = onFavoriteToggle,
                indication = null,
                interactionSource = remember { MutableInteractionSource() })
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else
                Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = if (isFavorite) Color.Red else Color.Gray
        )
    }
}
