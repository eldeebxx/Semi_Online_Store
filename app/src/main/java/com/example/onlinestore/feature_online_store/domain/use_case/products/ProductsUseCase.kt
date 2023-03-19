package com.example.onlinestore.feature_online_store.domain.use_case.products

data class ProductsUseCase(
    val getProducts: GetProducts,
    val getProductsPerCategory: GetProductsPerCategory,
    val getProductDetails: GetProductDetails
)