package rs.symphony.cicak.webshop.presentation.ui.categories

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rs.symphony.cicak.webshop.domain.Category
import rs.symphony.cicak.webshop.domain.Product
import rs.symphony.cicak.webshop.presentation.ui.home.HomeScreenState

sealed class CategoriesScreenState {
    object Loading : CategoriesScreenState()
    data class Success(val categories: List<Category>) : CategoriesScreenState()
    data class Error(val message: String) : CategoriesScreenState()
}

class CategoriesViewModel : ViewModel() {

    private val _screenState =
        MutableStateFlow<CategoriesScreenState>(CategoriesScreenState.Loading)
    val screenState: StateFlow<CategoriesScreenState> = _screenState

    fun fetchCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(600L)

            val items = List(15) {
                Category(it.toLong(), "Category $it", it.toDouble() * 10)
            }

            _screenState.value = CategoriesScreenState.Success(items)
        }
    }
}