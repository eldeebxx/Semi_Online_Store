package com.example.onlinestore.feature_online_store.data.remote.dto

import com.example.onlinestore.feature_online_store.domain.model.Rating

data class RatingDto(
    val count: Int,
    val rate: Double
) {
    fun toRating(): Rating {
        return Rating(
            count = count,
            rate = rate
        )
    }
}