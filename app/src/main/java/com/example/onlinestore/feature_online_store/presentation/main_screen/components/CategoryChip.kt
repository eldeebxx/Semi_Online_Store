package com.example.onlinestore.feature_online_store.presentation.main_screen.components

import androidx.compose.animation.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChip(
    title: String,
    isSelected: Boolean,
    onSelectionChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    ElevatedFilterChip(modifier = modifier,
        selected = isSelected,
        onClick = {
            onSelectionChanged(title)
        },
        label = {
            Text(text = title.uppercase())
        },
        leadingIcon = {
            AnimatedVisibility(
                visible = isSelected,
                enter = fadeIn() + slideInHorizontally(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                Icon(
                    imageVector = Icons.Filled.Done, contentDescription = null,
                )
            }
        }
    )
}