package rs.symphony.cicak.webshop.presentation.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rs.symphony.cicak.webshop.data.repository.CategoryRepository
import rs.symphony.cicak.webshop.domain.Category

sealed class CategoriesScreenState {
    object Loading : CategoriesScreenState()
    data class Success(val categories: List<Category>) : CategoriesScreenState()
    data class Error(val message: String) : CategoriesScreenState()
}

class CategoriesViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _screenState =
        MutableStateFlow<CategoriesScreenState>(CategoriesScreenState.Loading)
    val screenState: StateFlow<CategoriesScreenState> = _screenState

    private var _firstLoad = true

    fun observeCategories() {
        viewModelScope.launch {
            categoryRepository.getRootCategories().collect { categories ->
                if (categories.isEmpty() && _firstLoad) {
                    _screenState.value = CategoriesScreenState.Loading
                } else {
                    _screenState.value = CategoriesScreenState.Success(categories)
                }
            }
        }
    }

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                // Only show loading the first time categories are fetched
                if (_firstLoad) {
                    _firstLoad = false
                }

                categoryRepository.fetchRootCategories()

            } catch (e: Exception) {
                _screenState.value = CategoriesScreenState.Error("Failed to load categories")
            }
        }
    }
}