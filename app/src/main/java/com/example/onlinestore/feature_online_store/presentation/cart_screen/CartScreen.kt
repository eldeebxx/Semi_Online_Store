package com.example.onlinestore.feature_online_store.presentation.cart_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.onlinestore.feature_online_store.presentation.cart_screen.components.CartBottom
import com.example.onlinestore.feature_online_store.presentation.cart_screen.components.CartItemCard
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {

    val state = viewModel.cartItemsState.value
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    val totalPrice by viewModel.cartTotalPrice
    val isCartButtonEnabled by viewModel.isCheckoutBtnEnabled

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CartViewModel.UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                title = {
                    Column {
                        Text(text = "Your", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "Shopping Cart",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            )
        },
        bottomBar = {
            CartBottom(
                totalPrice = totalPrice,
                isCartButtonEnabled = isCartButtonEnabled,
                startCheckOut = {
                    // TODO
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.cartItems) { cartEntity ->
                    CartItemCard(
                        cartItem = cartEntity.toCartItem(),
                        removeItemFromCart = {
                            viewModel.onEvent(CartEvent.RemoveItemFromCart(cartEntity))
                            scope.launch {
                                snackBarHostState.currentSnackbarData?.dismiss()
                                val results = snackBarHostState.showSnackbar(
                                    "${cartEntity.title} Deleted.",
                                    actionLabel = "Undo"
                                )
                                if (results == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(CartEvent.RestoreCartItem)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}