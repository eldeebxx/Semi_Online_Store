package com.example.onlinestore.feature_online_store.presentation.details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.onlinestore.R
import com.example.onlinestore.feature_online_store.data.local.entity.CartEntity
import com.example.onlinestore.feature_online_store.presentation.cart_screen.CartEvent
import com.example.onlinestore.feature_online_store.presentation.cart_screen.CartViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    navController: NavController,
    viewModel: ProductDetailsViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {

    val state = viewModel.productDetails.value

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ProductDetailsViewModel.UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }, topBar = {
        CenterAlignedTopAppBar(navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
            }
        }, title = {
            Text(
                text = state.product?.title ?: "",
                modifier = Modifier.fillMaxWidth(0.6f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        })
    }) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(state.product?.image)
                        .crossfade(true).build(),
                    placeholder = painterResource(id = R.drawable.no_image),
                    contentDescription = state.product?.title ?: "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(300.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.Center)
                )


                Text(
                    text = "${state.product?.rating?.rate}/10 (${state.product?.rating?.count})",
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            MaterialTheme.colorScheme.onBackground.copy(
                                alpha = 0.5f
                            )
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }


            Spacer(modifier = Modifier.height(12.dp))

            Text(text = state.product?.title ?: "", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "${state.product?.price} $", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = state.product?.description ?: "", style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            ElevatedButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {
                cartViewModel.onEvent(
                    CartEvent.AddItemToCart(
                        cartEntity = CartEntity(
                            itemId = state.product?.id ?: 0,
                            title = state.product?.title ?: "",
                            image = state.product?.image ?: "",
                            price = state.product?.price ?: 0.0,
                            quantity = 1,
                        )
                    )
                )
                scope.launch {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(
                        "${state.product?.title} added to your Cart."
                    )
                }
            }) {
                Text(text = "Add to Cart")
            }
        }
    }
}