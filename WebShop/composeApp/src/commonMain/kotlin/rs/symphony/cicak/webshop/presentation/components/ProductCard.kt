package rs.symphony.cicak.webshop.presentation.components

import androidx.compose.foundation.background
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
import rs.symphony.cicak.webshop.presentation.ui.main.Transparent

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
        modifier = modifier.fillMaxWidth(),
        backgroundColor = Transparent,
        elevation = 0.dp,
        onClick = { onItemClicked(item.id) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                KamelImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
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
                
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.title,
                    style = MaterialTheme.typography.h6,
                    color = Cyan
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${item.price}$",
                        style = MaterialTheme.typography.body1,
                        color = Cyan
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
            tint = Cyan
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
            tint = Cyan
        )
    }
}
