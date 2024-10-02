package rs.symphony.cicak.webshop.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import rs.symphony.cicak.webshop.domain.Category

@Composable
fun CategoryCard(item: Category) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Make the item square
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Text(text = item.name)
            }
        }
    }
}
