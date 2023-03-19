package com.example.onlinestore.feature_online_store.presentation.details_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinestore.feature_online_store.domain.use_case.products.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _productDetails = mutableStateOf(ProductDetailsState())
    val productDetails: State<ProductDetailsState> = _productDetails

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentProductId: Int? = null

    init {
        savedStateHandle.get<Int>("productId")?.let { productId ->
            if (productId != -1) {
                viewModelScope.launch {
                    productsUseCase.getProductDetails(productId)?.also { product ->
                        currentProductId = product.id

                        _productDetails.value = productDetails.value.copy(
                            product = product,
                            isLoading = false
                        )
                    }
                }
            }

        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}