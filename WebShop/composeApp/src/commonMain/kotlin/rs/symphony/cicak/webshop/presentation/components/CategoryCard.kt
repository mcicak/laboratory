package rs.symphony.cicak.webshop.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import rs.symphony.cicak.webshop.domain.Category

@Composable
fun CategoryCard(item: Category) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        KamelImage(
            modifier = Modifier.aspectRatio(1f),
            resource = asyncPainterResource(item.image),
            contentDescription = null
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = item.name,
            style = MaterialTheme.typography.subtitle2,
            textAlign = TextAlign.Center
        )
    }
}
