package com.example.onlinestore.feature_online_store.presentation.cart_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.math.BigDecimal

@Composable
fun CartBottom(
    totalPrice: BigDecimal,
    isCartButtonEnabled: Boolean,
    startCheckOut: () -> Unit
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Divider(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .border(
                    2.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Cart Total")
            Text(text = "$totalPrice $")
        }

        ElevatedButton(
            modifier = Modifier.fillMaxWidth(0.8f),
            onClick = startCheckOut, enabled = isCartButtonEnabled
        ) {
            Text(text = "CheckOut")
        }
    }
}