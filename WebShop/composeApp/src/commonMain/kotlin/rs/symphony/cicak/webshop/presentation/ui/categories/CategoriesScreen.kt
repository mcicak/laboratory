package rs.symphony.cicak.webshop.presentation.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rs.symphony.cicak.webshop.presentation.components.CategoryCard
import rs.symphony.cicak.webshop.presentation.components.Title
import rs.symphony.cicak.webshop.presentation.ui.main.Transparent
import rs.symphony.cicak.webshop.presentation.util.getPlatformPadding

@Composable
fun CategoriesScreen(viewModel: CategoriesViewModel, onCategoryClick: (String) -> Unit) {
    val state by viewModel.screenState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeCategories()
    }

    Scaffold(
        backgroundColor = Transparent,
        topBar = {
            Title(
                modifier = Modifier.padding(16.dp, top = 16.dp + getPlatformPadding()),
                text = "Categories"
            )
        }
    ) { padding ->
        when (state) {
            is CategoriesScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is CategoriesScreenState.Success -> {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val categories = (state as CategoriesScreenState.Success).categories
                    items(categories.size) { index ->
                        CategoryCard(item = categories[index], onClick = onCategoryClick)
                    }
                }
            }

            is CategoriesScreenState.Error -> {
                val error = (state as CategoriesScreenState.Error).message
                Text(text = error)
            }
        }
    }
}
