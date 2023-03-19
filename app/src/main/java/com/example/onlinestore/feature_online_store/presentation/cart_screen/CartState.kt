package com.example.onlinestore.feature_online_store.presentation.cart_screen

import com.example.onlinestore.feature_online_store.data.local.entity.CartEntity

data class CartState(
    val cartItems: List<CartEntity> = emptyList(),
    val isLoading: Boolean = false
)
