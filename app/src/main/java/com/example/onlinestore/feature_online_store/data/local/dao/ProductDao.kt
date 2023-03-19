package com.example.onlinestore.feature_online_store.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onlinestore.feature_online_store.data.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(products: List<ProductEntity>)

    @Query("DELETE FROM products_table WHERE title IN (:products)")
    suspend fun deleteProducts(products: List<String>)

    @Query("SELECT * FROM products_table")
    suspend fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM products_table WHERE category LIKE :category")
    suspend fun getProductsPerCategory(category: String): List<ProductEntity>

    @Query("SELECT * FROM products_table WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity

}