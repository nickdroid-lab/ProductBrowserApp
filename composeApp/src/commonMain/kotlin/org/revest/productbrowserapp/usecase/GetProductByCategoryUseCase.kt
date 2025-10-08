package org.revest.productbrowserapp.usecase

import org.revest.productbrowserapp.models.ProductListResponse
import org.revest.productbrowserapp.repository.ProductRepository

class GetProductByCategoryUseCase(private val repo: ProductRepository) {
    suspend operator fun invoke(category: String, limit: Int = 30): ProductListResponse {
        return repo.getProductsByCategory(category, limit)
    }
}