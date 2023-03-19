package com.example.onlinestore.feature_online_store.domain.model

data class CartItem(
    val itemId: Int,
    val title: String,
    val image: String,
    val price: Double,
    val quantity: Int,
)
