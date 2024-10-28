package rs.symphony.cicak.webshop.presentation.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.domain.ProductId
import rs.symphony.cicak.webshop.presentation.components.ProductCard
import rs.symphony.cicak.webshop.presentation.ui.main.Cyan
import rs.symphony.cicak.webshop.presentation.ui.main.PinkNeon
import rs.symphony.cicak.webshop.presentation.ui.main.PurpleDark
import rs.symphony.cicak.webshop.presentation.ui.main.Transparent

@Composable
fun ProductDetailsScreen(productId: ProductId, onBack: () -> Unit, onRecommendedProductClick: (ProductId) -> Unit) {

    val productViewModel = koinViewModel<ProductViewModel>(
        parameters = { parametersOf(productId) }
    )
    val product = productViewModel.getProduct(productId)
    val isFavorite by productViewModel.isFavorite(productId).collectAsState(initial = false)

    val recommended by productViewModel.recommendedProducts.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.loadRecommendedProducts()
    }

    val listState = rememberLazyListState()

    val scrollOffset = calculateTotalScrollOffset(listState)
    val topBarAlpha = if (scrollOffset <= 300) 1f - (scrollOffset / 300f) else 0f
    val backgroundColor = Color.White.copy(alpha = 1f - topBarAlpha)

    Box(modifier = Modifier.fillMaxSize()) {
        // Product content (image, details, etc.) without padding
        ProductPage(
            product = product,
            recommended = recommended,
            listState = listState,
            isFavorite = isFavorite,
            onAddToCart = { productViewModel.addToCart(productId) },
            onFavorite = { productViewModel.toggleFavorite(productId) },
            onRecommendedProductClick = onRecommendedProductClick
        )

        // TopBar is placed on top of the content
        TopBar(backgroundColor = backgroundColor, onBack = onBack)
    }
}

@Composable
fun TopBar(backgroundColor: Color, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .height(70.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(start = 8.dp, top = 20.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White)
                .size(40.dp)
                .clickable(onClick = onBack)
        ) {
            Icon(
                modifier = Modifier.align(alignment = Alignment.Center),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

@Composable
fun ProductPage(
    product: Product,
    recommended: List<Product>,
    listState: LazyListState,
    isFavorite: Boolean,
    onAddToCart: () -> Unit,
    onFavorite: () -> Unit,
    onRecommendedProductClick: (ProductId) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(PinkNeon, PurpleDark))),
        state = listState
    ) {
        item {
            KamelImage(
                modifier = Modifier.fillMaxWidth(),
                resource = asyncPainterResource(product.images.first()),
                contentDescription = null
            )
        }
        item {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Cyan
                )
                Text(text = product.subtitle, color = Cyan)
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                ) {
                    Text("Price: ", color = Cyan)
                    Text(
                        text = product.price.toString() + " " + product.currency,
                        fontWeight = FontWeight.Bold,
                        color = Cyan
                    )
                }
            }
        }

        item {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "Description",
                    fontWeight = FontWeight.Bold,
                    color = Cyan
                )
                Text(text = product.description, color = Cyan)
            }
        }

        item {
            ProductButtons(isFavorite = isFavorite, onAddToCart, onFavorite)
        }

        item {
            RecommendedItems(recommended, onRecommendedProductClick)
        }
    }
}

@Composable
fun RecommendedItems(recommended: List<Product>, onRecommendedProductClick: (ProductId) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Recommended Products",
            fontWeight = FontWeight.Bold,
            color = Cyan
        )

        val chunkedItems = recommended.chunked(2) // Split into rows of 2 products
        chunkedItems.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { product ->
                    ProductCard(
                        modifier = Modifier
                            .weight(1f) // Each product takes equal space
                            .padding(8.dp),
                        item = product,
                        isFavorite = false,
                        onItemClicked = { onRecommendedProductClick(product.id) }
                    )
                }
                // Handle cases where the row has less than 2 items
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f)) // Fill the remaining space
                }
            }
        }
    }
}

@Composable
private fun ProductButtons(isFavorite: Boolean, onAddToCart: () -> Unit, onFavorite: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProductButton(
            modifier = Modifier.weight(1f),
            onClick = { onFavorite() },
            icon = if (isFavorite) Icons.Filled.Favorite else
                Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite"
        )
        ProductButton(
            modifier = Modifier.weight(1f),
            onClick = { onAddToCart() },
            icon = Icons.Default.ShoppingCart,
            contentDescription = "Cart"
        )
    }
}

@Composable
private fun ProductButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String
) {
    Button(
        modifier = modifier
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF18164a), // Neon Purple
                        Color(0xFFf80b70)  // Neon Pink
                    )
                )
            )
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF00FFEE), // Neon Cyan
                        Color(0xFFFFE600)  // Neon Yellow
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
        //.shadow(8.dp, RoundedCornerShape(16.dp)),
        ,
        shape = RoundedCornerShape(16.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Transparent,
            contentColor = Color.White // Icon color
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color(0xFFd0dff7) // Neon yellow icon for contrast
        )
    }
}

// Function to calculate total scroll offset including all previous items' heights
private fun calculateTotalScrollOffset(listState: LazyListState): Int {
    val layoutInfo = listState.layoutInfo
    val visibleItems = layoutInfo.visibleItemsInfo

    // If there are no visible items, return 0
    if (visibleItems.isEmpty()) return 0

    // Get the first visible item index and offset
    val firstVisibleItem = visibleItems.first()
    val firstVisibleItemIndex = listState.firstVisibleItemIndex
    val firstVisibleItemScrollOffset = listState.firstVisibleItemScrollOffset

    // Calculate total offset by adding the cumulative height of all previous items to the scroll offset
    var totalOffset = firstVisibleItemScrollOffset
    for (i in 0 until firstVisibleItemIndex) {
        val itemHeight = visibleItems.firstOrNull { it.index == i }?.size ?: firstVisibleItem.size
        totalOffset += itemHeight
    }

    return totalOffset
}
