package rs.symphony.cicak.webshop.presentation.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rs.symphony.cicak.webshop.domain.Product

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Success(val products: List<Product>) : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
}


class HomeViewModel : ViewModel() {

    private val _homeScreenState = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState

    fun fetchHomeProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(600L)

            // Simulate some network or database fetching

            val productList = List(100) {
                Product(it.toLong(), "Product $it", it.toDouble() * 10)
            }
            // Update the product list
            _homeScreenState.value = HomeScreenState.Success(productList)
            //_products.update { productList }
        }
    }
}