package com.example.kaffeeapp.presentation.main.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kaffeeapp.repository.SelectedType


@Composable
fun DrinksSelectTypeSection(
    modifier: Modifier,
    drinkSelectedType: SelectedType,
    onClickType: (SelectedType) -> Unit
) {
    Row(
        modifier = modifier
    ) {
        SelectedType.entries.forEach { type ->
            SelectTypeCard(
                type = type,
                drinkSelectedType = drinkSelectedType,
                onClickType = { selectedType -> onClickType.invoke(selectedType) }
            )
        }
    }
}