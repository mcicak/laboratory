package rs.symphony.cicak.webshop.presentation.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CartScreen(viewModel: CartViewModel) {

    val cartItems by viewModel.cartItems.collectAsState()
    val totalCost by viewModel.totalCost.collectAsState()

    Scaffold(
        topBar = {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Your Cart",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    ) { padding ->

        if (cartItems.isNotEmpty()) {
            CartList(padding, viewModel)
        } else {
            EmptyCartView(padding)
        }
    }
}

@Composable
fun CartList(padding: PaddingValues, viewModel: CartViewModel) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalCost by viewModel.totalCost.collectAsState()

    LazyColumn {
        items(cartItems.size) { index ->
            val cartItem = cartItems[index]
            Text(text = "Product ID: ${cartItem.productId} - Quantity: ${cartItem.quantity}")

            // Add buttons to update quantity or remove items
            Row {
                Button(onClick = {
                    viewModel.updateCartItemQuantity(
                        cartItem.productId,
                        cartItem.quantity + 1
                    )
                }) {
                    Text("Increase")
                }
                Button(onClick = {
                    viewModel.updateCartItemQuantity(
                        cartItem.productId,
                        cartItem.quantity - 1
                    )
                }) {
                    Text("Decrease")
                }
                Button(onClick = { viewModel.removeFromCart(cartItem.productId) }) {
                    Text("Remove")
                }
            }
        }

        item {
            // Display total cost
            Text(text = "Total Cost: $totalCost")
        }
    }


}

@Composable
private fun EmptyCartView(padding: PaddingValues) {
    val lightGrayColor = Color.LightGray

    Column(
        modifier = Modifier.fillMaxSize().padding(padding),
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