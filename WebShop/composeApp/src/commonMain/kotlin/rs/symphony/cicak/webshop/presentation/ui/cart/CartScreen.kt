@file:OptIn(ExperimentalMaterialApi::class)

package rs.symphony.cicak.webshop.presentation.ui.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay
import rs.symphony.cicak.webshop.domain.Currency
import rs.symphony.cicak.webshop.presentation.components.Title
import rs.symphony.cicak.webshop.presentation.ui.main.Cyan
import rs.symphony.cicak.webshop.presentation.ui.main.PinkNeon
import rs.symphony.cicak.webshop.presentation.ui.main.Purple2
import rs.symphony.cicak.webshop.presentation.ui.main.PurpleDark
import rs.symphony.cicak.webshop.presentation.ui.main.Transparent
import rs.symphony.cicak.webshop.presentation.util.getPlatformPadding

@Composable
fun CartScreen(viewModel: CartViewModel) {
    val cartItems by viewModel.cartItemsUi.collectAsState()

    Scaffold(
        backgroundColor = Transparent,
        topBar = {
            Title(
                modifier = Modifier.padding(16.dp, top = 16.dp + getPlatformPadding(), bottom = 20.dp),
                text = "Your Cart"
            )
        }
    ) { padding ->

        if (cartItems.isNotEmpty()) {
            FullCartView(padding, viewModel)
        } else {
            EmptyCartView(padding)
        }
    }
}

@Composable
fun FullCartView(padding: PaddingValues, viewModel: CartViewModel) {
    val cartItems by viewModel.cartItemsUi.collectAsState()
    val totalCost by viewModel.totalCost.collectAsState(initial = 0.0)
    //val currency by viewModel.currency.collectAsState()

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize() // Ensures the Column takes the full height of the parent
    ) {
        // Content that will take the rest of the space
        Box(
            modifier = Modifier
                .weight(1f) // Takes up the remaining space
                .fillMaxWidth()
        ) {
            CartItemsListView(cartItems, Currency.USD, onDelete = {
                viewModel.removeFromCart(it.product.id)
            }, onDecrease = {
                viewModel.decreaseQuantity(it)
            }, onIncrease = {
                viewModel.increaseQuantity(it)
            })
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            FooterCheckoutView(totalCost, Currency.USD)
        }
    }
}

@Composable
private fun CartItemsListView(
    cartItems: List<CartItemUi>,
    currency: Currency,
    onDelete: (CartItemUi) -> Unit,
    onIncrease: (CartItemUi) -> Unit,
    onDecrease: (CartItemUi) -> Unit
) {
    LazyColumn(
        //verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(cartItems.count(), key = { cartItems[it].product.id }) { index ->
            val cartItem = cartItems[index]
            SwipeToDeleteContainer(
                cartItem,
                onDelete = onDelete
            ) {
                CartItemRow(cartItem, currency, onIncrease = onIncrease, onDecrease = onDecrease)
            }
        }
    }
}

@Composable
private fun CartItemRow(
    cartItem: CartItemUi,
    currency: Currency,
    onIncrease: (CartItemUi) -> Unit,
    onDecrease: (CartItemUi) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Purple2, PurpleDark)
                )
                //color = PurpleDark
            )
            .height(85.dp)
            .padding(start = 8.dp, end = 8.dp)
    ) {
        KamelImage(
            modifier = Modifier
                .height(80.dp)
                .align(alignment = Alignment.CenterVertically)
                .aspectRatio(1f),
            resource = asyncPainterResource(cartItem.product.images.first()),
            contentDescription = null
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 4.dp),
            //.background(color = Color.Blue),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                modifier = Modifier
                    //.background(color = Color.Yellow)
                    .align(alignment = Alignment.TopStart)
            ) {
                Text(
                    modifier = Modifier.padding(top = 8.dp, bottom = 2.dp),
                    text = cartItem.product.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Cyan,
                    style = MaterialTheme.typography.subtitle2
                )
            }

            Row(
                modifier = Modifier
                    //.background(color = Color.Yellow)
                    .padding(bottom = 2.dp)
                    .align(alignment = Alignment.BottomStart),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    modifier = Modifier.padding(end = 2.dp, bottom = 6.dp),
                    text = cartItem.product.price.toString(),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Cyan
                )
                Text(
                    modifier = Modifier
                        .offset(y = 1.5.dp)
                        .align(alignment = Alignment.Bottom),
                    text = currency.symbol,
                    color = Cyan
                )
            }

            Row(
                modifier = Modifier
                    .padding(bottom = 4.dp, end = 4.dp)
                    //.background(color = Color.Cyan)
                    .align(alignment = Alignment.BottomEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .border(0.5.dp, Cyan)
//                        .background(color = Color.Yellow)
                        .size(30.dp)
                        .clickable(
                            onClick = { onDecrease(cartItem) }
                        ),
                    contentAlignment = Alignment.Center) {
                    Text(text = "-", fontSize = 20.sp, color = Cyan)
                }
                Text(
                    modifier = Modifier.width(30.dp),
                    textAlign = TextAlign.Center,
                    text = cartItem.quantity.toString(),
                    color = Cyan
                )
                Box(
                    modifier = Modifier
                        .border(0.5.dp, Cyan)
//                        .background(color = Color.Yellow)
                        .size(30.dp)
                        .clickable(
                            onClick = { onIncrease(cartItem) }
                        ),
                    contentAlignment = Alignment.Center) {
                    Text(text = "+", fontSize = 20.sp, color = Cyan)
                }
            }

            Box(
                modifier = Modifier
                    .align(alignment = Alignment.BottomStart)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = PinkNeon)
            )
        }
    }
}

@Composable
private fun FooterCheckoutView(totalCost: Double, currency: Currency) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Total cost: $totalCost ${currency.symbol}", color = Cyan, style = MaterialTheme.typography.subtitle2)
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {
            print("CONTINUE")
        }) {
            Text("CHECKOUT")
        }
    }
}

@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberDismissState(

        confirmStateChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                DeleteBackground(swipeDismissState = state)
            }
        ) {
            content(item)
        }

//        SwipeToDismissBox(
//            enableDismissFromStartToEnd = false,
//            state = state,
//            backgroundContent = {
//                DeleteBackground(swipeDismissState = state)
//            },
//            content = { content(item) },
//        )
    }
}

@Composable
fun DeleteBackground(
    swipeDismissState: DismissState
) {
    val color = if (swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        Color.Red
    } else Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
private fun EmptyCartView(padding: PaddingValues) {
    val lightGrayColor = Cyan

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(160.dp),
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null,
            colorFilter = ColorFilter.tint(lightGrayColor)
        )
        Text(
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
            text = "Empty Cart",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = lightGrayColor
        )
        Text(
            text = "Products added to your cart will be displayed here.",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = lightGrayColor
        )
    }
}
