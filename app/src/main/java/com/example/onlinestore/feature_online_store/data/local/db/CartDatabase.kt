package com.example.onlinestore.feature_online_store.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.onlinestore.feature_online_store.data.local.Converter
import com.example.onlinestore.feature_online_store.data.local.dao.CartDao
import com.example.onlinestore.feature_online_store.data.local.entity.CartEntity

@Database(
    entities = [CartEntity::class],
    version = 1, exportSchema = false
)
@TypeConverters(Converter::class)
abstract class CartDatabase : RoomDatabase() {
    abstract val dao: CartDao
}