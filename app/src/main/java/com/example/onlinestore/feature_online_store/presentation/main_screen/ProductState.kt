package com.example.onlinestore.feature_online_store.presentation.main_screen

import com.example.onlinestore.feature_online_store.domain.model.Product

data class ProductState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false
)
