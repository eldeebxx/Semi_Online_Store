package com.example.onlinestore.feature_online_store.domain.repository

import com.example.onlinestore.core.util.Resource
import com.example.onlinestore.feature_online_store.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getProductsList(): Flow<Resource<List<Product>>>

    fun getProductsPerCategory(category: String): Flow<Resource<List<Product>>>

    suspend fun getProductDetails(id: Int): Product?
}