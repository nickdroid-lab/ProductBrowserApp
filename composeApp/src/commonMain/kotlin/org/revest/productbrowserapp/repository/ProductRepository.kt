package org.revest.productbrowserapp.repository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.revest.productbrowserapp.models.Category
import org.revest.productbrowserapp.models.Product
import org.revest.productbrowserapp.models.ProductListResponse

class ProductRepository(private val client: HttpClient) {

    private val baseUrl = "https://dummyjson.com"

    suspend fun getProducts(limit: Int = 30, skip: Int = 0): ProductListResponse {
        return client.get("$baseUrl/products?limit=$limit&skip=$skip").body()
    }

    suspend fun getProduct(productId: Int): Product {
        return client.get("$baseUrl/products/$productId").body()
    }

    suspend fun searchProducts(query: String, limit: Int = 30): ProductListResponse {
        return client.get("$baseUrl/products/search?q=$query&limit=$limit").body()
    }

    suspend fun getCategories(): List<Category> {
        return client.get("$baseUrl/products/categories").body()
    }

    suspend fun getProductsByCategory(category: String, limit: Int = 30): ProductListResponse {
        return client.get("$baseUrl/products/category/$category?limit=$limit").body()
    }
}
