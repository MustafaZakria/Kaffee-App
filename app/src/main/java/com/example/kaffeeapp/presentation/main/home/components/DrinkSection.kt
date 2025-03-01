package com.example.kaffeeapp.presentation.main.home.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.EmptyList
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.util.Constants.NETWORK_ERROR
import com.example.kaffeeapp.util.model.Resource

@Composable
fun DrinksSection(
    drinks: List<Drink>,
    drinksResponse: Resource<Boolean>,
    onClickDrink: (String) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment =
        (if (drinksResponse is Resource.Loading || drinks.isEmpty())
            Alignment.Center
        else Alignment.TopStart)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            items(drinks) { drink ->
                DrinkCard(
                    drink = drink,
                    onClickDrink = { id -> onClickDrink.invoke(id) }
                )
            }
        }
        if (drinksResponse is Resource.Loading && drinks.isEmpty()) {
            ProgressBar()
        } else if (drinks.isEmpty()) {
            EmptyList(stringResource(id = R.string.empty_list))
        }
        LaunchedEffect(key1 = drinksResponse) {
            if (drinksResponse is Resource.Failure) {
                Toast.makeText(context, NETWORK_ERROR, Toast.LENGTH_SHORT).show()
            }
        }
    }
}