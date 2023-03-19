package com.example.onlinestore.feature_online_store.data.local.dao

import androidx.room.*
import com.example.onlinestore.feature_online_store.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_table")
    fun getAllCartItems(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartEntity)

    @Query("UPDATE cart_table SET quantity = quantity + 1 WHERE itemId = :id")
    suspend fun updateQuantity(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM cart_table WHERE itemId = :id)")
    suspend fun isRowIsExist(id: Int): Boolean

    @Delete
    suspend fun deleteCartItem(item: CartEntity)

    @Query("SELECT * FROM cart_table WHERE id = :id")
    suspend fun getCartItemById(id: Int): CartEntity

}