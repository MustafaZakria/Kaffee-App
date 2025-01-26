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
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.presentation.main.cart.components.AfterOrderList
import com.example.kaffeeapp.presentation.main.cart.components.BeforeOrderList
import com.example.kaffeeapp.presentation.main.cart.components.BottomSheetContent
import com.example.kaffeeapp.presentation.main.cart.components.CartBottomBar
import com.example.kaffeeapp.presentation.main.cart.components.NoteDialog
import com.example.kaffeeapp.presentation.main.cart.components.OrderCard
import com.example.kaffeeapp.presentation.main.cart.components.SelectDeliverMethod
import com.example.kaffeeapp.presentation.main.cart.models.CartScreenAction
import com.example.kaffeeapp.presentation.main.cart.models.CartScreenAction.BranchSelected
import com.example.kaffeeapp.presentation.main.cart.models.CartScreenAction.DeliveryEnabledChanged
import com.example.kaffeeapp.presentation.main.cart.models.CartScreenAction.NoteSaved
import com.example.kaffeeapp.presentation.main.cart.models.CartScreenAction.OrderDeleted
import com.example.kaffeeapp.presentation.main.cart.models.CartScreenAction.OrderSubmitted
import com.example.kaffeeapp.presentation.main.cart.models.CartScreenAction.PhoneChanged
import com.example.kaffeeapp.presentation.main.cart.models.CartScreenAction.PromoCodeApplied
import com.example.kaffeeapp.presentation.main.cart.models.CartScreenAction.QuantityChanged
import com.example.kaffeeapp.presentation.main.cart.models.InputValidationResult
import com.example.kaffeeapp.presentation.main.cart.models.OrderUi
import com.example.kaffeeapp.repository.interfaces.BranchesResult
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.util.Constants.NOTE_ADDED_SUCCESSFULLY
import com.example.kaffeeapp.util.Utils.removeBadgeOnCart
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.util.snackbarStuff.SnackbarController
import com.example.kaffeeapp.util.snackbarStuff.SnackbarEvent
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    viewModel: CartViewModel,
    navigateToMapScreen: () -> Unit
) {
    val orderUi = viewModel.orderUi
    val orderSubmissionResult = viewModel.orderSubmissionResult
    val inputsHandler = viewModel.inputValidationResult
    val branches = viewModel.branches

    if (orderUi.drinkOrders.isEmpty()) {
        removeBadgeOnCart()
    }

    CartScreenContent(
        orderUi = orderUi,
        orderSubmissionResult = orderSubmissionResult,
        inputsHandler = inputsHandler,
        branches = branches,
        navigateToMapScreen = { navigateToMapScreen.invoke() },
        onUserAction = { action -> viewModel.handleUserAction(action) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenContent(
    orderUi: OrderUi,
    inputsHandler: InputValidationResult,
    orderSubmissionResult: Resource<Boolean>?,
    branches: BranchesResult,
    navigateToMapScreen: () -> Unit,
    onUserAction: (CartScreenAction) -> Unit
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
                onSelectBranch = ::BranchSelected
            )
        },
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,

        ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = dimensionResource(id = R.dimen.padding_bottom_navigation),
                )
        ) {
            if (orderUi.drinkOrders.isNotEmpty()) {
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
                        isDeliveryEnabled = orderUi.isDeliveryEnabled,
                        onDeliveryEnabledChange = { onUserAction.invoke(DeliveryEnabledChanged(it)) }
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
                                orderUi = orderUi,
                                errorHandler = inputsHandler,
                                navigateToMapScreen = { navigateToMapScreen.invoke() },
                                onPhoneValueChange = { onUserAction.invoke(PhoneChanged(it)) },
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
                        itemsIndexed(orderUi.drinkOrders) { index, order ->
                            OrderCard(
                                order = order,
                                orderPrice = (order.price.toDouble() * order.quantity).toString(),
                                onDeleteClick = { onUserAction.invoke(OrderDeleted(index)) },
                                onQuantityChange = {
                                    onUserAction.invoke(
                                        QuantityChanged(
                                            index,
                                            it
                                        )
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
                                orderUi = orderUi,
                                errorHandler = inputsHandler,
                                isDeliveryEnabled = orderUi.isDeliveryEnabled,
                                onApplyPromoClick = { onUserAction.invoke(PromoCodeApplied(it)) },
                            )
                            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
                        }
                    }
                    CartBottomBar(
                        orderTotalCost = orderUi.getTotalCost(),
                        onOrderClick = { onUserAction.invoke(OrderSubmitted) }
                    )
                    if (showDialog) {
                        NoteDialog(
                            noteValue = orderUi.note,
                            onNoteValueChange = ::NoteSaved,
                            onDismiss = {
                                onUserAction.invoke(NoteSaved(orderUi.note))
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
        orderResult = orderSubmissionResult
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
    val orderUi = OrderUi(drinkOrders = orders)
    KaffeeAppTheme {
//        CartScreenContent(
//            {},
//            { _, _ -> },
//            {},
//            {},
//            {},
//            {},
//            orderUi,
//            InputValidationResult(),
//            null,
//            {},
//            {},
//            Resource.Loading(),
//            {}
//        )
    }
}