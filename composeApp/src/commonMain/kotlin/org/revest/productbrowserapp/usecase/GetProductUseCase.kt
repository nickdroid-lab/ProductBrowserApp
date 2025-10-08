package org.revest.productbrowserapp.usecase

import org.revest.productbrowserapp.models.ProductListResponse
import org.revest.productbrowserapp.repository.ProductRepository

class GetProductsUseCase(private val repo: ProductRepository) {
    suspend operator fun invoke(limit: Int = 30, skip: Int = 0): ProductListResponse {
        return repo.getProducts(limit, skip)
    }
}
