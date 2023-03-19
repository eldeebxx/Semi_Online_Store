package com.example.onlinestore.feature_online_store.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.onlinestore.feature_online_store.domain.model.Product
import com.example.onlinestore.feature_online_store.domain.model.Rating

@Entity(tableName = "products_table")
data class ProductEntity(
    @PrimaryKey val id: Int? = null,
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
){
    fun toProduct() : Product {
        return Product(
            id = id,
            category = category,
            description = description,
            image = image,
            price = price,
            rating = rating,
            title = title
        )
    }
}
