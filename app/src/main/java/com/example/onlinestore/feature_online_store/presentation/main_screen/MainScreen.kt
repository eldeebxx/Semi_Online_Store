package com.example.onlinestore.feature_online_store.presentation.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.onlinestore.R
import com.example.onlinestore.feature_online_store.presentation.main_screen.components.CategoriesList
import com.example.onlinestore.feature_online_store.presentation.main_screen.components.ProductCard
import com.example.onlinestore.feature_online_store.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    var selectedCategory by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ProductViewModel.UIEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.CartScreen.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(4.dp)
        ) {
            Text(text = "Filter by Category")
            CategoriesList(
                list = state.products.map { it.category }.distinctBy { it },
                selectedCategory = selectedCategory,
                onSelectedChanged = { selected ->
                    selectedCategory =
                        state.products.first { it.category == selected }.category
                    viewModel.onEvent(ProductsEvent.FilterProducts(selected))
                }
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = if (selectedCategory == "") "All Products".uppercase() else selectedCategory.uppercase(),

                )

            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items((if (selectedCategory == "")
                    state.products
                else
                    state.products.filter { it.category == selectedCategory }
                        )) { product ->
                    ProductCard(
                        product = product,
                        modifier = Modifier
                            .height(300.dp)
                            .clickable {
                                navController.navigate(Screen.ItemDetails.route + "?productId=${product.id}")
                            }
                    )
                }
            }
        }

    }
}