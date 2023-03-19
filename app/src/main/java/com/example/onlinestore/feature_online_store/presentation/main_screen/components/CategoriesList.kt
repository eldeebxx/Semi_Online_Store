package com.example.onlinestore.feature_online_store.presentation.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoriesList(
    list: List<String>,
    selectedCategory: String? = null,
    onSelectedChanged: (String) -> Unit = {}
) {
    Column(modifier = Modifier.padding(8.dp)) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list) {
                CategoryChip(
                    title = it,
                    isSelected = selectedCategory == it,
                    onSelectionChanged = {
                        onSelectedChanged(it)
                    }
                )
            }
        }
    }

}