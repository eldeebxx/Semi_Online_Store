package com.example.onlinestore.feature_online_store.presentation.cart_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinestore.feature_online_store.data.local.entity.CartEntity
import com.example.onlinestore.feature_online_store.domain.use_case.cart.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCase: CartUseCase
) : ViewModel() {

    private val _cartItemsState = mutableStateOf(CartState())
    val cartItemsState: State<CartState> = _cartItemsState

    val cartTotalPrice: State<BigDecimal>
        get() = mutableStateOf(_cartItemsState.value.cartItems.sumOf {
            it.price * it.quantity
        }.toBigDecimal())

    val isCheckoutBtnEnabled: State<Boolean>
        get() = _cartItemsState.run { mutableStateOf(_cartItemsState.value.cartItems.isNotEmpty()) }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var recentlyDeletedCartItem: CartEntity? = null


    private var loadCartItemsJob: Job? = null

    init {
        getCartItems()
    }

    private fun getCartItems() {
        loadCartItemsJob?.cancel()
        loadCartItemsJob = viewModelScope.launch {
            cartUseCase.getCartItems().onEach { result ->
                _cartItemsState.value = _cartItemsState.value.copy(
                    cartItems = result,
                    isLoading = false
                )
            }.launchIn(this)
        }

    }

    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.RemoveItemFromCart -> {
                viewModelScope.launch {
                    cartUseCase.removeCartItem(event.cartEntity)
                    recentlyDeletedCartItem = event.cartEntity
                }
            }
            is CartEvent.AddItemToCart -> {
                viewModelScope.launch {
                    val cartItem = cartUseCase.isItemExists(event.cartEntity)
                    if (!cartItem) {
                        cartUseCase.addItemToCart(event.cartEntity)
                    } else {
                        cartUseCase.updateCartQuantity(event.cartEntity)
                    }
                }
            }
            is CartEvent.RestoreCartItem -> {
                viewModelScope.launch {
                    cartUseCase.addItemToCart(recentlyDeletedCartItem ?: return@launch)
                    recentlyDeletedCartItem = null
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}