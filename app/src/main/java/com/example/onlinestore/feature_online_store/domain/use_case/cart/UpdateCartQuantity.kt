package com.example.onlinestore.feature_online_store.domain.use_case.cart

import com.example.onlinestore.feature_online_store.data.local.entity.CartEntity
import com.example.onlinestore.feature_online_store.domain.repository.CartRepository

class UpdateCartQuantity(private val repository: CartRepository) {
    suspend operator fun invoke(cartEntity: CartEntity) {
        return repository.updateQuantity(
            cartEntity = cartEntity
        )
    }
}