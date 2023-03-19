package com.example.onlinestore.feature_online_store.domain.use_case.cart

data class CartUseCase(
    val getCartItems: GetCartItems,
    val isItemExists: IsItemExists,
    val updateCartQuantity: UpdateCartQuantity,
    val addItemToCart: AddItemToCart,
    val removeCartItem: RemoveCartItem
)