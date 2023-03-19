package com.example.onlinestore.feature_online_store.presentation.util

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object ItemDetails : Screen("item_details")
    object CartScreen : Screen("cart_screen")
}
