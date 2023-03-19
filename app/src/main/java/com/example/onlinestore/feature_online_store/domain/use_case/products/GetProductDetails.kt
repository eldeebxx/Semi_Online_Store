package com.example.onlinestore.feature_online_store.domain.use_case.products

import com.example.onlinestore.feature_online_store.domain.model.Product
import com.example.onlinestore.feature_online_store.domain.repository.ProductsRepository

class GetProductDetails(private val repository: ProductsRepository) {
    suspend operator fun invoke(id: Int): Product? {
        return repository.getProductDetails(id)
    }
}