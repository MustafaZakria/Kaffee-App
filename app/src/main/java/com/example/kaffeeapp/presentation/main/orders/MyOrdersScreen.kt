package com.example.kaffeeapp.presentation.main.orders

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.EmptyList
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.data.entities.Order
import com.example.kaffeeapp.presentation.main.orders.components.MyOrderCard
import com.example.kaffeeapp.presentation.main.orders.components.TopBarMyOrdersScreen
import com.example.kaffeeapp.presentation.main.profile.ProfileViewModel
import com.example.kaffeeapp.repository.interfaces.OrdersResponse
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.util.Constants.ADDRESS_KEY
import com.example.kaffeeapp.util.Constants.NETWORK_ERROR
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.util.snackbarStuff.SnackbarController
import com.example.kaffeeapp.util.snackbarStuff.SnackbarEvent

@Composable
fun MyOrdersScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateBackToProfileScreen: () -> Unit
) {
    val ordersResponse = viewModel.orders.collectAsState(initial = Resource.Loading())

    MyOrdersScreenContent(
        ordersResponse = ordersResponse.value,
        navigateBack = { navigateBackToProfileScreen.invoke() }
    )
}

@Composable
fun MyOrdersScreenContent(
    ordersResponse: OrdersResponse,
    navigateBack: () -> Unit
) {
    Scaffold(containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopBarMyOrdersScreen(
                onBackButtonClick = {
                    navigateBack.invoke()
                }
            )
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = 0.dp,
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val ordersList = if (ordersResponse is Resource.Success) {
                    ordersResponse.data ?: emptyList()
                } else emptyList()
                items(ordersList) { order ->
                    MyOrderCard(
                        order
                    )
                }
            }
            if (ordersResponse is Resource.Loading) {
                ProgressBar(
                    modifier = Modifier.fillMaxSize()
                )
            } else if (ordersResponse.data?.isEmpty() == true) {
                EmptyList(stringResource(id = R.string.empty_list))
            }
            LaunchedEffect(key1 = ordersResponse) {
                if (ordersResponse is Resource.Failure) {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = NETWORK_ERROR
                        )
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MyOrdersScreenPreview() {
    val orders = listOf(
        DrinkOrder(
            name = "Americano",
            quantity = 1,
            size = "S",
            price = "35.0"
        ),
        DrinkOrder(
            name = "Coffee",
            quantity = 2,
            size = "M",
            price = "35.0"
        )
    )
    val order = Order(
        timestamp = 1727572163360,
        isHomeDeliveryOrder = true,
        drinkOrders = orders,
        totalPrice = "101.0",
        deliveryDetails = mapOf(ADDRESS_KEY to "23 Dokki St.")
    )
    KaffeeAppTheme(
        darkTheme = true
    ) {
        MyOrdersScreenContent(
            ordersResponse = Resource.Success(listOf(order))
        ) {

        }
    }
}
