package com.example.onlinestore.feature_online_store.data.remote

import com.example.onlinestore.feature_online_store.data.remote.dto.ProductDto
import retrofit2.http.GET

interface ProductsApi {

    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    companion object {
        const val BASE_URL = "https://fakestoreapi.com/"
    }
}