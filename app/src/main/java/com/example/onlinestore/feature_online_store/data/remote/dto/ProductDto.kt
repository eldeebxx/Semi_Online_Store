package com.example.onlinestore.feature_online_store.data.remote.dto

import com.example.onlinestore.feature_online_store.data.local.entity.ProductEntity

data class ProductDto(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: RatingDto,
    val title: String
) {
    fun toProductEntity(): ProductEntity {
        return ProductEntity(
            id = id,
            category = category,
            description = description,
            image = image,
            price = price,
            rating = rating.toRating(),
            title = title
        )
    }
}