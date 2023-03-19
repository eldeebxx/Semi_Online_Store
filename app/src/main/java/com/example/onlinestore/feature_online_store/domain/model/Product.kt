package com.example.onlinestore.feature_online_store.domain.model

data class Product(
    val category: String,
    val description: String,
    val id: Int?,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)
