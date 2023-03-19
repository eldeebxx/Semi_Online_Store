package com.example.onlinestore.feature_online_store.presentation.main_screen

sealed class ProductsEvent {
    data class FilterProducts(val category: String) : ProductsEvent()
}
