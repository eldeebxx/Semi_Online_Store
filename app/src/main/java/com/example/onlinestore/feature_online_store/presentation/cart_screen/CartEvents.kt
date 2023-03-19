package com.example.onlinestore.feature_online_store.presentation.cart_screen

import com.example.onlinestore.feature_online_store.data.local.entity.CartEntity

sealed class CartEvent {
    data class RemoveItemFromCart(val cartEntity: CartEntity) : CartEvent()
    data class AddItemToCart(val cartEntity: CartEntity) : CartEvent()
    object RestoreCartItem : CartEvent()
}
