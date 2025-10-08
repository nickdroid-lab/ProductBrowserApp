package org.revest.productbrowserapp.usecase

import org.revest.productbrowserapp.models.Category
import org.revest.productbrowserapp.repository.ProductRepository

class GetCategoriesUseCase(private val repo: ProductRepository) {
    suspend operator fun invoke(): List<Category> {
        return repo.getCategories()
    }
}