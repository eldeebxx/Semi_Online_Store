package com.example.onlinestore.feature_online_store.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.onlinestore.feature_online_store.domain.model.CartItem

@Entity(tableName = "cart_table")
data class CartEntity(
    @PrimaryKey val id: Int? = null,
    val itemId: Int,
    val title: String,
    val image: String,
    val price: Double,
    val quantity: Int,
) {
    fun toCartItem(): CartItem {
        return CartItem(
            itemId = itemId,
            title = title,
            image = image,
            price = price,
            quantity = quantity,
        )
    }
}
