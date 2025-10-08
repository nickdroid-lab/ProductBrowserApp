package org.revest.productbrowserapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.revest.productbrowserapp.network.createHttpClient
import org.revest.productbrowserapp.repository.ProductRepository
import org.revest.productbrowserapp.ui.ProductDetail
import org.revest.productbrowserapp.ui.ProductList
import org.revest.productbrowserapp.usecase.GetCategoriesUseCase
import org.revest.productbrowserapp.usecase.GetProductByCategoryUseCase
import org.revest.productbrowserapp.usecase.GetProductsUseCase
import org.revest.productbrowserapp.usecase.SearchProductsUseCase
import org.revest.productbrowserapp.viewmodel.ProductViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val scope = rememberCoroutineScope()
    val repo = remember { ProductRepository(createHttpClient()) }
    val getProductsUseCase = remember { GetProductsUseCase(repo) }
    val searchProductsUseCase = remember { SearchProductsUseCase(repo) }
    val categoryUseCase = remember { GetCategoriesUseCase(repo) }
    val productByCategoriesUseCase = remember { GetProductByCategoryUseCase(repo) }
    val viewModel = remember {
        ProductViewModel(
            getProductsUseCase,
            searchProductsUseCase, categoryUseCase,
            productByCategoriesUseCase, scope
        )
    }

    val uiState by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (uiState.selectedProduct == null)
                            "Products"
                        else "Product Details"
                    )
                },
                navigationIcon = {
                    if (uiState.selectedProduct != null)
                    IconButton(onClick = { viewModel.clearSelection() }) {
                        BackIcon(
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                uiState.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                uiState.error != null -> Text(
                    "Error: ${uiState.error}",
                    modifier = Modifier.align(Alignment.Center)
                )
                uiState.selectedProduct == null -> ProductList(
                    uiState = uiState,
                    onSearchChanged = { viewModel.onSearchInput(it) },
                    onCategorySelected = { viewModel.selectCategory(it) },
                    onProductClick = { viewModel.selectProduct(it) }
                )
                else -> ProductDetail(
                    product = uiState.selectedProduct!!,
                    onBack = { viewModel.clearSelection() }
                )
            }
        }
    }
}