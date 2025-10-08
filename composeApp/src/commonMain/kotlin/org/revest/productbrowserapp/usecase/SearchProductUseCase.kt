package org.revest.productbrowserapp.usecase
import org.revest.productbrowserapp.models.ProductListResponse
import org.revest.productbrowserapp.repository.ProductRepository

class SearchProductsUseCase(private val repo: ProductRepository) {
    suspend operator fun invoke(query: String, limit: Int = 30): ProductListResponse {
        return repo.searchProducts(query, limit)
    }
}
