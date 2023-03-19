package com.example.onlinestore.feature_online_store.presentation.details_screen

import com.example.onlinestore.feature_online_store.domain.model.Product

data class ProductDetailsState(
    val product: Product? = null,
    val isLoading: Boolean = false
)
