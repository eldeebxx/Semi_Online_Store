package com.example.onlinestore.feature_online_store.domain.repository

import com.example.onlinestore.feature_online_store.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItemsList(): Flow<List<CartEntity>>
    suspend fun removeCartItem(cartEntity: CartEntity)
    suspend fun isItemIsExist(cartEntity: CartEntity): Boolean
    suspend fun updateQuantity(cartEntity: CartEntity)
    suspend fun addItemToCart(cartEntity: CartEntity)
}