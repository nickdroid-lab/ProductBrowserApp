package org.revest.productbrowserapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.revest.productbrowserapp.NetworkImage
import org.revest.productbrowserapp.models.Product
import org.revest.productbrowserapp.viewmodel.ProductUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductList(
    uiState: ProductUiState,
    onSearchChanged: (String) -> Unit,
    onCategorySelected :(String?) -> Unit,
    onProductClick: (Product) -> Unit) {

    var expanded by remember { mutableStateOf(false) }


    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onSearchChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Search products...") },
            singleLine = true
        )
        if (uiState.categories.isNotEmpty()) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.selectedCategory ?: "All Categories",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Filter by Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("All Categories") },
                        onClick = {
                            expanded = false
                            onCategorySelected(null)
                        }
                    )
                    uiState.categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                expanded = false
                                onCategorySelected(category.name)
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (uiState.products.isEmpty() && !uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No products found")
            }
        } else {
            LazyColumn {
                items(uiState.products) { p ->
                    ProductRow(p, onProductClick)
                }
            }
        }
    }
}


@Composable
fun ProductRow(p: Product, onProductClick: (Product) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { onProductClick(p) }
        .padding(8.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)) {
            Box(modifier = Modifier.size(64.dp)) {
                // Multiplatform image loading placeholder
                // Replace with actual image loading for each platform
                //Text("Img", modifier = Modifier.align(Alignment.Center))
                if(p.thumbnail.isNotEmpty())

                    NetworkImage(
                        url = p.thumbnail, // The URL of the image to load
                        contentDescription = "A network image", // For accessibility
                        modifier = Modifier
                            .fillMaxWidth() // Makes the image fill the box
                            .aspectRatio(16f / 9f) // Sets the aspect ratio of the image
                        // You can add other modifiers here, like .clip() for rounded corners
                    ) else Text("Img", modifier = Modifier.align(Alignment.Center))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = p.title, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "₹${p.price}")
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}