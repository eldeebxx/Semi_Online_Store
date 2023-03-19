package com.example.onlinestore.feature_online_store.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.onlinestore.feature_online_store.presentation.cart_screen.CartScreen
import com.example.onlinestore.feature_online_store.presentation.details_screen.ProductDetailsScreen
import com.example.onlinestore.feature_online_store.presentation.main_screen.MainScreen
import com.example.onlinestore.feature_online_store.presentation.main_screen.ProductViewModel
import com.example.onlinestore.feature_online_store.presentation.util.Screen
import com.example.onlinestore.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: ProductViewModel by viewModels()
                    val snackbarHostState = remember {
                        SnackbarHostState()
                    }

                    LaunchedEffect(key1 = true) {
                        viewModel.eventFlow.collectLatest { event ->
                            when (event) {
                                is ProductViewModel.UIEvent.ShowSnackBar -> {
                                    snackbarHostState.showSnackbar(
                                        message = event.message
                                    )
                                }
                            }
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = Screen.MainScreen.route
                    ) {
                        composable(route = Screen.MainScreen.route) {
                            MainScreen(navController = navController)
                        }

                        composable(Screen.ItemDetails.route + "?productId={productId}",
                            arguments = listOf(
                                navArgument("productId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            // TODO details screen
                            ProductDetailsScreen(navController = navController)
                        }

                        composable(route = Screen.CartScreen.route) {
                            CartScreen(navController = navController)
                        }

                    }

                }
            }
        }
    }
}
