package org.revest.productbrowserapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.revest.shared.viewmodel.ProductViewModel
import org.revest.productbrowserapp.network.createHttpClient
import org.revest.productbrowserapp.repository.ProductRepository
import org.revest.productbrowserapp.ui.ProductDetail
import org.revest.productbrowserapp.ui.ProductList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val scope = rememberCoroutineScope()
    val repo = remember { ProductRepository(createHttpClient()) }
    val viewModel = remember { ProductViewModel(repo, scope) }

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
                    onSearchChanged = { viewModel.search(it) },
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