package com.example.kaffeeapp.presentation.main.orders

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.EmptyList
import com.example.kaffeeapp.components.ImageLoaderWithUrl
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.data.entities.Order
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.presentation.main.profile.ProfileViewModel
import com.example.kaffeeapp.repository.interfaces.OrdersResponse
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.ui.theme.lightWhite
import com.example.kaffeeapp.util.Constants.ADDRESS
import com.example.kaffeeapp.util.Constants.BRANCH_ADDRESS
import com.example.kaffeeapp.util.Constants.NETWORK_ERROR
import com.example.kaffeeapp.util.model.Resource

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
            val context = LocalContext.current
            if (ordersResponse is Resource.Loading) {
                ProgressBar(
                    modifier = Modifier.fillMaxSize()
                )
            } else if (ordersResponse.data?.isEmpty() == true) {
                EmptyList(stringResource(id = R.string.empty_list))
            }
            LaunchedEffect(key1 = ordersResponse) {
                if (ordersResponse is Resource.Failure) {
                    Toast.makeText(context, NETWORK_ERROR, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun MyOrderCard(
    order: Order
) {
    val location = if (order.isHomeDeliveryOrder)
        order.deliveryDetails[ADDRESS]
    else order.deliveryDetails[BRANCH_ADDRESS]
    val date = order.getFormattedDate()
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DateAndLocationSection(
                date = date,
                location = location
            )
            order.drinkOrders.forEach { drinkOrder ->
                SingleDrinkOrderCard(order = drinkOrder)
            }
            HorizontalDivider(modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_x_small))
            )
            TotalPriceSection(
                totalPrice = order.totalPrice
            )
        }
    }
}

@Composable
fun TotalPriceSection(totalPrice: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomizedText(
            text = stringResource(id = R.string.total),
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.headlineSmall,
            textLines = 1,
        )
        CustomizedText(
            text = stringResource(id = R.string.cash),
            fontWeight = FontWeight.Normal,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
            textLines = 1,
        )
        CustomizedText(
            text = stringResource(id = R.string.drink_price, totalPrice),
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.headlineSmall,
            textLines = 1,
        )
    }
}

@Composable
fun SingleDrinkOrderCard(order: DrinkOrder) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.lightWhite
        )
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        ) {
            //image
            Box(
                modifier = Modifier.align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center
            ) {
                ImageLoaderWithUrl(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size_55))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_6))),
                    imageUrl = order.imageUrl
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.Top)
                    .weight(1f)
                    .height(dimensionResource(id = R.dimen.image_size_55)),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CustomizedText(
                    text = order.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    color = MaterialTheme.colorScheme.onTertiary,
                    style = MaterialTheme.typography.headlineSmall,
                    textLines = 1,
                )
                CustomizedText(
                    text = order.size,
                    fontSize = dimensionResource(id = R.dimen.text_size_medium),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            CustomizedText(
                text = stringResource(id = R.string.drink_quantity_text, order.quantity),
                fontSize = dimensionResource(id = R.dimen.text_size_medium),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            CustomizedText(
                text = stringResource(id = R.string.drink_price, (order.price.toDouble() * order.quantity)),
                fontSize = dimensionResource(id = R.dimen.text_size_medium),
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun DateAndLocationSection(date: String, location: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.calendar_icon),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
        )
        CustomizedText(
            text = date,
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.headlineSmall,
            textLines = 1,
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.location_icon),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
        )
        CustomizedText(
            text = location.toString(),
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.headlineSmall,
            textLines = 1,
        )
    }
}

@Composable
fun TopBarMyOrdersScreen(onBackButtonClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.padding_x_large),
                start = dimensionResource(id = R.dimen.padding_medium),
                end = dimensionResource(id = R.dimen.padding_medium),
                bottom = dimensionResource(id = R.dimen.padding_medium),
            )
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onBackButtonClick.invoke()
        })
        {
            Icon(
                painter = painterResource(id = R.drawable.back_icon),
                contentDescription = stringResource(id = R.string.back_img_desc),
                tint = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }
        CustomizedText(
            text = stringResource(id = R.string.my_orders),
            fontSize = dimensionResource(id = R.dimen.text_size_18),
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
        )
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.back_icon),
                contentDescription = stringResource(id = R.string.heart_img_desc),
                tint = Color.Transparent,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
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
        deliveryDetails = mapOf(ADDRESS to "23 Dokki St.")
    )
    KaffeeAppTheme {
        MyOrdersScreenContent(
            ordersResponse = Resource.Success(listOf(order))
        ) {

        }
    }
}
