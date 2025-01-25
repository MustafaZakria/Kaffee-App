package com.example.kaffeeapp.presentation.main.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.EmptyList
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.components.TopBarTitle
import com.example.kaffeeapp.data.entities.BranchDetails
import com.example.kaffeeapp.data.entities.DeliveryType
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.presentation.main.cart.components.AfterOrderList
import com.example.kaffeeapp.presentation.main.cart.components.BeforeOrderList
import com.example.kaffeeapp.presentation.main.cart.components.BottomSheetContent
import com.example.kaffeeapp.presentation.main.cart.components.CartBottomBar
import com.example.kaffeeapp.presentation.main.cart.components.NoteDialog
import com.example.kaffeeapp.presentation.main.cart.components.OrderCard
import com.example.kaffeeapp.presentation.main.cart.components.SelectDeliverMethod
import com.example.kaffeeapp.presentation.main.cart.models.CartDetails
import com.example.kaffeeapp.presentation.main.cart.models.CartInputsHandler
import com.example.kaffeeapp.repository.interfaces.BranchesResult
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.util.Constants.NOTE_ADDED_SUCCESSFULLY
import com.example.kaffeeapp.util.model.OrderCost
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.util.snackbarStuff.SnackbarController
import com.example.kaffeeapp.util.snackbarStuff.SnackbarEvent
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    viewModel: CartViewModel,
    navigateToMapScreen: () -> Unit
) {
    val orderCost = viewModel.orderCost
    val cartDetails = viewModel.cartDetails
    val orderResultState = viewModel.submitOrderResponse
    val inputsHandler = viewModel.cartInputsHandler
    val branches = viewModel.branches.value

    if (cartDetails.drinkOrders.isEmpty()) {
        viewModel.removeBadgeOnCart()
    }

    CartScreenContent(
        orderCost = orderCost,
        cartDetails = cartDetails,
        orderResultState = orderResultState,
        inputsHandler = inputsHandler,
        onDeleteOrderClick = { index -> viewModel.removeDrinkFromCart(index) },
        onChangeQuantityClick = { index, newValue ->
            viewModel.setDrinkOrderQuantity(
                index,
                newValue
            )
        },
        onApplyPromoClick = { code -> viewModel.setPromoCode(code) },
        onDeliveryEnableChange = { isEnabled -> viewModel.setDeliveryEnabledValue(isEnabled) },
        navigateToMapScreen = { navigateToMapScreen.invoke() },
        onPhoneValueChange = { value -> viewModel.onPhoneNumberValueChange(value) },
        onOrderClick = { viewModel.submitOrder() },
        onSaveNote = { value -> viewModel.setNoteValue(value) },
        branches = branches,
        onSelectBranch = { branch ->
            viewModel.setDeliveryMethod(
                DeliveryType.BranchDelivery(branch.name, branch.address)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenContent(
    onDeleteOrderClick: (Int) -> Unit,
    onChangeQuantityClick: (Int, Int) -> Unit,
    onApplyPromoClick: (String) -> Unit,
    orderCost: OrderCost,
    onDeliveryEnableChange: (Boolean) -> Unit,
    navigateToMapScreen: () -> Unit,
    onPhoneValueChange: (String) -> Unit,
    cartDetails: CartDetails,
    inputsHandler: CartInputsHandler,
    orderResultState: Resource<Boolean>?,
    onOrderClick: () -> Unit,
    onSaveNote: (String) -> Unit,
    branches: BranchesResult,
    onSelectBranch: (BranchDetails) -> Unit
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        topBar = { TopBarTitle(title = stringResource(id = R.string.cart)) },
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.surface,
        sheetContainerColor = MaterialTheme.colorScheme.tertiary,
        sheetContent = {
            BottomSheetContent(
                branchesResult = branches,
                onSelectBranch = { branch -> onSelectBranch.invoke(branch) }
            )
        },
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
//        sheetDragHandle = {}

    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = dimensionResource(id = R.dimen.padding_bottom_navigation),
                )
        ) {
            if (cartDetails.drinkOrders.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    //selecting pick up way
                    SelectDeliverMethod(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_medium),
                                end = dimensionResource(id = R.dimen.padding_medium)
                            ),
                        isDeliveryEnabled = cartDetails.isDeliveryEnabled,
                        onDeliveryClick = {
                            onDeliveryEnableChange.invoke(true)
                        },
                        onPickUpClick = {
                            onDeliveryEnableChange.invoke(false)
                        }
                    )
                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
                    //drink orders
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_medium),
                                end = dimensionResource(id = R.dimen.padding_medium)
                            ),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            BeforeOrderList(
                                cartDetails = cartDetails,
                                errorHandler = inputsHandler,
                                navigateToMapScreen = { navigateToMapScreen.invoke() },
                                onPhoneValueChange = { value -> onPhoneValueChange.invoke(value) },
                                onAddNoteClick = { showDialog = true },
                                onSelectBranchClick = {
                                    scope.launch {
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                }
                            )
                            //spacer
                            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
                            HorizontalDivider(color = MaterialTheme.colorScheme.secondary)
                            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_x_small)))
                        }
                        itemsIndexed(cartDetails.drinkOrders) { index, order ->
                            OrderCard(
                                order = order,
                                orderPrice = (order.price.toDouble() * order.quantity).toString(),
                                onDeleteClick = { onDeleteOrderClick.invoke(index) },
                                onPlusClick = {
                                    onChangeQuantityClick.invoke(
                                        index,
                                        order.quantity + 1
                                    )
                                },
                                onMinusClick = {
                                    onChangeQuantityClick.invoke(
                                        index,
                                        order.quantity - 1
                                    )
                                }
                            )
                        }
                        item {
                            //spacer
                            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_x_small)))
                            HorizontalDivider(color = MaterialTheme.colorScheme.secondary)
                            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))

                            AfterOrderList(
                                orderCost = orderCost,
                                errorHandler = inputsHandler,
                                isDeliveryEnabled = cartDetails.isDeliveryEnabled,
                                onApplyPromoClick = { code -> onApplyPromoClick.invoke(code) }
                            )
                            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
                        }
                    }
                    CartBottomBar(
                        orderCost = orderCost,
                        onOrderClick = {
                            onOrderClick.invoke()
                        }
                    )
                    if (showDialog) {
                        NoteDialog(
                            noteValue = cartDetails.note,
                            onNoteValueChange = { value ->
                                onSaveNote.invoke(value)
                            },
                            onDismiss = {
                                onSaveNote.invoke("")
                                showDialog = false
                            },
                            onSaveNote = {
                                showDialog = false
                                scope.launch {
                                    SnackbarController.sendEvent(
                                        SnackbarEvent(
                                            message = NOTE_ADDED_SUCCESSFULLY
                                        )
                                    )
                                }
                            }
                        )
                    }
                }
            } else {
                EmptyList(message = stringResource(id = R.string.no_orders))
            }
        }
    }
    OnResultState(
        orderResult = orderResultState
    )
}

@Composable
fun OnResultState(
    orderResult: Resource<Boolean>?,
) {
    if (orderResult is Resource.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.35f))
        ) {
            ProgressBar(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun CartScreenPreview() {
    val orders = mutableListOf(
        DrinkOrder(
            name = "Americano",
            id = "1",
            size = "S",
            price = "35.0"
        ),
        DrinkOrder(
            name = "Coffee",
            id = "2",
            size = "M",
            price = "35.0"
        )
    )
    val cartDetails = CartDetails(drinkOrders = orders)
    KaffeeAppTheme {
        CartScreenContent(
            {},
            { _, _ -> },
            {},
            OrderCost(),
            {},
            {},
            {},
            cartDetails,
            CartInputsHandler(),
            null,
            {},
            {},
            Resource.Loading(),
            {}
        )
    }
}