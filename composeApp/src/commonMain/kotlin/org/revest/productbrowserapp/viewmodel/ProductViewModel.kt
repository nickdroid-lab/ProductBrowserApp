package com.revest.shared.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.revest.productbrowserapp.models.Category
import org.revest.productbrowserapp.models.Product
import org.revest.productbrowserapp.repository.ProductRepository

data class ProductUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val products: List<Product> = emptyList(),
    val selectedProduct: Product? = null,
    val searchQuery: String = "",
    val categories: List<Category> = emptyList(),
    val selectedCategory: String? = null
)

class ProductViewModel(
    private val repo: ProductRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {

    private val _state = MutableStateFlow(ProductUiState(isLoading = true))
    val state: StateFlow<ProductUiState> = _state

    init {
        loadCategories()
        loadProducts()
    }

    fun loadProducts(category: String? = null) {
        _state.update { it.copy(isLoading = true, error = null) }
        scope.launch {
            try {
                val response = when {
                    !category.isNullOrBlank() -> repo.getProductsByCategory(category)
                    else -> repo.getProducts(20)
                }
                _state.update { it.copy(isLoading = false, products = response.products, error = null) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun search(query: String) {
        _state.update { it.copy(searchQuery = query, isLoading = true) }
        scope.launch {
            try {
                val response = if (query.isBlank()) repo.getProducts(20)
                else repo.searchProducts(query)
                _state.update { it.copy(isLoading = false, products = response.products, error = null) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun selectProduct(product: Product) {
        _state.update { it.copy(selectedProduct = product) }
    }

    fun clearSelection() {
        _state.update { it.copy(selectedProduct = null) }
    }

    fun loadCategories() {
        scope.launch {
            try {
                val result = repo.getCategories()
                _state.update { it.copy(categories = result) }
            } catch (e: Exception) {
                // Ignore silently if category fetch fails
            }
        }
    }
    fun selectCategory(category: String?) {
        _state.update { it.copy(selectedCategory = category) }
        loadProducts(category)
    }
}
