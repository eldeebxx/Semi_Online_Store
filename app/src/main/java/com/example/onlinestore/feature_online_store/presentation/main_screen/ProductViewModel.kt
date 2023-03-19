package com.example.onlinestore.feature_online_store.presentation.main_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinestore.core.util.Resource
import com.example.onlinestore.feature_online_store.domain.use_case.products.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ProductState())
    val state: State<ProductState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var loadProductsJob: Job? = null

    init {
        loadProducts()
    }

    fun onEvent(event: ProductsEvent) {
        when (event) {
            is ProductsEvent.FilterProducts -> {
                viewModelScope.launch {
                    productsUseCase.getProductsPerCategory(event.category)
                }
            }
        }
    }

    fun loadProducts() {
        loadProductsJob?.cancel()
        loadProductsJob =
            viewModelScope.launch {
                delay(500)
                productsUseCase.getProducts()
                    .onEach { result ->
                        Log.d("loadProducts", "loadProducts -> Called")

                        when (result) {
                            is Resource.Success -> {
                                _state.value = state.value.copy(
                                    products = result.data ?: emptyList(),
                                    isLoading = false
                                )
                                Log.d("loadProducts", "loadProducts -> Success")
                            }
                            is Resource.Error -> {
                                _state.value = state.value.copy(
                                    products = result.data ?: emptyList(),
                                    isLoading = false
                                )

                                Log.d("loadProducts", "loadProducts -> Error")

                                _eventFlow.emit(
                                    UIEvent.ShowSnackBar(
                                        result.message ?: "Unknown Error"
                                    )
                                )
                            }
                            is Resource.Loading -> {
                                _state.value = state.value.copy(
                                    products = result.data ?: emptyList(),
                                    isLoading = true
                                )
                                Log.d("loadProducts", "loadProducts -> Loading")
                            }
                        }
                    }.launchIn(this)
            }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }

}