package com.example.onlinestore.feature_online_store.data.repository

import com.example.onlinestore.feature_online_store.data.local.dao.CartDao
import com.example.onlinestore.feature_online_store.data.local.entity.CartEntity
import com.example.onlinestore.feature_online_store.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(
    private val dao: CartDao,
) : CartRepository {
    override fun getCartItemsList(): Flow<List<CartEntity>> {
        return dao.getAllCartItems()
    }

    override suspend fun removeCartItem(cartEntity: CartEntity) {
        dao.deleteCartItem(item = cartEntity)
    }

    override suspend fun isItemIsExist(cartEntity: CartEntity): Boolean {
        return dao.isRowIsExist(cartEntity.itemId)
    }

    override suspend fun updateQuantity(cartEntity: CartEntity) {
        dao.updateQuantity(cartEntity.itemId)
    }

    override suspend fun addItemToCart(cartEntity: CartEntity) {
        dao.insertCartItem(cartEntity)
    }
}