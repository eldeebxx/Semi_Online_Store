package com.example.onlinestore.feature_online_store.domain.use_case.products

import com.example.onlinestore.core.util.Resource
import com.example.onlinestore.feature_online_store.domain.model.Product
import com.example.onlinestore.feature_online_store.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow

class GetProductsPerCategory(
    private val repository: ProductsRepository
) {
    operator fun invoke(category: String): Flow<Resource<List<Product>>> {
        return repository.getProductsPerCategory(category = category)
    }
}
