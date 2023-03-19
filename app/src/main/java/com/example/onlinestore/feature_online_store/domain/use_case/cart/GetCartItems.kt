package com.example.onlinestore.feature_online_store.domain.use_case.cart

import com.example.onlinestore.feature_online_store.data.local.entity.CartEntity
import com.example.onlinestore.feature_online_store.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class GetCartItems(private val repository: CartRepository) {
    operator fun invoke(): Flow<List<CartEntity>> {
        return repository.getCartItemsList()
    }
}