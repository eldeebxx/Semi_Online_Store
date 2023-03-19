package com.example.onlinestore.feature_online_store.presentation.cart_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.onlinestore.R
import com.example.onlinestore.feature_online_store.domain.model.CartItem

@Composable
fun CartItemCard(
    modifier: Modifier = Modifier,
    cartItem: CartItem,
    removeItemFromCart: () -> Unit
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cartItem.image)
                    .scale(Scale.FIT)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 4.dp, end = 8.dp)
            ) {
                Text(
                    text = cartItem.title, maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(modifier = Modifier.align(Alignment.End)) {
                    Text(text = stringResource(id = R.string.cart_quantity, cartItem.quantity))
                    Text(text = "${cartItem.price} $")
                }
            }

            IconButton(onClick = removeItemFromCart) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null,
                    tint = Color.Red.copy(
                        alpha = 0.7f
                    ),
                    modifier = Modifier.size(34.dp)
                )
            }

        }
    }
}