package com.example.kaffeeapp.presentation.main.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.EmptyList
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.components.TopBarTitle
import com.example.kaffeeapp.data.entities.DeliveryType
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.presentation.main.cart.components.CartBottomBar
import com.example.kaffeeapp.presentation.main.cart.components.OrderCard
import com.example.kaffeeapp.presentation.main.cart.components.PaymentSummarySection
import com.example.kaffeeapp.presentation.main.cart.components.RoundedButtonWithIcon
import com.example.kaffeeapp.presentation.main.cart.models.CartDetails
import com.example.kaffeeapp.presentation.main.cart.models.CartInputsHandler
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.util.Constants.ADDRESS_ADDED_SUCCESSFULLY
import com.example.kaffeeapp.util.model.OrderCost
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.util.snackbarStuff.SnackbarController
import com.example.kaffeeapp.util.snackbarStuff.SnackbsrEvent

@Composable
fun CartScreen(
    viewModel: CartViewModel,
    navigateToMapScreen: () -> Unit
) {
    val orderCost = viewModel.orderCost
    val cartDetails = viewModel.cartDetails
    val orderResultState = viewModel.orderResponse
    val inputsHandler = viewModel.cartInputsHandler

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
    )
}

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
) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = { TopBarTitle(title = stringResource(id = R.string.cart)) },
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
                                navigateToMapScreen = { navigateToMapScreen.invoke() },
                                onPhoneValueChange = { value -> onPhoneValueChange.invoke(value) },
                                cartDetails = cartDetails,
                                errorHandler = inputsHandler
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
                }
            } else {
                EmptyList(message = stringResource(id = R.string.no_orders))
            }
        }
    }
    OnResultState(
        orderResult = orderResultState,
        deliveryDetail = cartDetails.deliveryValue,
    )
}

@Composable
fun OnResultState(
    orderResult: Resource<Boolean>?,
    deliveryDetail: DeliveryType?,
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
    LaunchedEffect(key1 = deliveryDetail) {
        if (deliveryDetail != null) {
            SnackbarController.sendEvent(
                SnackbsrEvent(
                    message = ADDRESS_ADDED_SUCCESSFULLY
                )
            )
        }
    }
}


@Composable
fun BeforeOrderList(
    navigateToMapScreen: () -> Unit,
    onPhoneValueChange: (String) -> Unit,
    cartDetails: CartDetails,
    errorHandler: CartInputsHandler
) {
    //delivery content
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomizedText(
            text = if (cartDetails.isDeliveryEnabled) stringResource(id = R.string.home_address) else stringResource(
                id = R.string.pick_up_branch
            ),
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))

        if (cartDetails.isDeliveryEnabled) {
            var address = ""
            if (cartDetails.deliveryValue != null) {
                address =
                    (cartDetails.deliveryValue as? DeliveryType.HomeDelivery)?.address ?: ""
            }
            DeliverySection(
                address = address,
                onEditAddressClick = {
                    navigateToMapScreen.invoke()
                },
                addressErrorValue = errorHandler.addressErrorValue,
                onAddNoteClick = {}
            )
        } else {
            var branchName = ""
            if (cartDetails.deliveryValue != null) {
                branchName =
                    (cartDetails.deliveryValue as? DeliveryType.BranchDelivery)?.branchAddress
                        ?: ""
            }
            PickUpSection(
                branch = branchName,
                addressErrorValue = errorHandler.addressErrorValue,
                onClick = {}
            )
        }

        //telephone number section
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        PhoneNumberContainer(
            onPhoneValueChange = { value ->
                onPhoneValueChange.invoke(value)
            },
            phoneErrorValue = errorHandler.phoneErrorValue,
            phoneNumber = cartDetails.phoneNumberValue
        )
    }
}

@Composable
fun AfterOrderList(
    orderCost: OrderCost,
    isDeliveryEnabled: Boolean,
    onApplyPromoClick: (String) -> Unit,
) {
    //promo code
    PromoCodeContainer(
        onApplyClick = { code -> onApplyPromoClick.invoke(code) }
    )
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))

    //payment summary
    PaymentSummarySection(
        orderCost = orderCost,
        isDeliveryEnabled = isDeliveryEnabled
    )
}

@Composable
fun SelectDeliverMethod(
    modifier: Modifier,
    isDeliveryEnabled: Boolean,
    onDeliveryClick: () -> Unit,
    onPickUpClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_12)),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_x_small)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clickable { onDeliveryClick.invoke() }
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_small)))
                    .background(if (isDeliveryEnabled) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .weight(1f)
            ) {
                CustomizedText(
                    text = stringResource(id = R.string.delivery),
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    fontWeight = FontWeight.Medium,
                    color = if (isDeliveryEnabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.padding_small))
                )
            }
            Box(
                modifier = Modifier
                    .clickable { onPickUpClick.invoke() }
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_small)))
                    .background(if (!isDeliveryEnabled) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .weight(1f)
            ) {
                CustomizedText(
                    text = stringResource(id = R.string.pick_up),
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.padding_small)),
                    color = if (!isDeliveryEnabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}

@Composable
fun PickUpSection(
    branch: String,
    addressErrorValue: String,
    onClick: () -> Unit
) {
    Column {
        RoundedButtonWithIcon(
            backgroundColor = MaterialTheme.colorScheme.tertiary,
            borderStroke = BorderStroke(
                1.dp,
                if (addressErrorValue.isNotBlank()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
            ),
            leadingIconId = R.drawable.edit_icon,
            leadingIconDesc = "edit",
            trailingIconId = R.drawable.arrow_right_icon,
            trailingIconDesc = "arrow right",
            color = MaterialTheme.colorScheme.primary,
            text = if (branch == "") stringResource(id = R.string.select_nearby_branch) else branch
        ) {
            onClick.invoke()
        }
        if(addressErrorValue.isNotBlank()) {
            CustomizedText(
                text = addressErrorValue,
                fontSize = dimensionResource(id = R.dimen.text_size_small),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }

}

@Composable
fun DeliverySection(
    address: String,
    addressErrorValue: String,
    onEditAddressClick: () -> Unit,
    onAddNoteClick: () -> Unit
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundedButtonWithIcon(
                modifier = Modifier.weight(1f),
                backgroundColor = MaterialTheme.colorScheme.tertiary,
                leadingIconId = R.drawable.edit_icon,
                leadingIconDesc = "edit",
                trailingIconId = R.drawable.arrow_right_icon,
                trailingIconDesc = "arrow right",
                color = MaterialTheme.colorScheme.primary,
                text = if (address == "") stringResource(id = R.string.add_address) else address,
                borderStroke = BorderStroke(
                    1.dp,
                    if (addressErrorValue.isNotBlank()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                )
            ) {
                onEditAddressClick.invoke()
            }
            RoundedButtonWithIcon(
                backgroundColor = MaterialTheme.colorScheme.tertiary,
                leadingIconId = R.drawable.document_icon,
                leadingIconDesc = "add",
                color = MaterialTheme.colorScheme.primary,
                text = stringResource(id = R.string.add_note),
                borderStroke = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline
                )
            ) {
                onAddNoteClick.invoke()
            }
        }
        if(addressErrorValue.isNotBlank()) {
            CustomizedText(
                text = addressErrorValue,
                fontSize = dimensionResource(id = R.dimen.text_size_small),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}


@Composable
fun PhoneNumberContainer(
    onPhoneValueChange: (String) -> Unit,
    phoneErrorValue: String,
    phoneNumber: String
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)))
                .background(MaterialTheme.colorScheme.tertiary),
        ) {
            TextField(
                value = phoneNumber,
                onValueChange = { value ->
                    onPhoneValueChange.invoke(value)
                },
                isError = (phoneErrorValue.isNotBlank()),
                singleLine = true,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
                placeholder = {
                    CustomizedText(
                        text = stringResource(id = R.string.phone_hint),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = dimensionResource(id = R.dimen.text_size_medium)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.phone_icon),
                        contentDescription = "Phone",
                        tint = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
                    )
                },
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onTertiary
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
        if(phoneErrorValue.isNotBlank()) {
            CustomizedText(
                text = phoneErrorValue,
                fontSize = dimensionResource(id = R.dimen.text_size_small),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }

}


@Composable
fun PromoCodeContainer(
    onApplyClick: (String) -> Unit
) {
    var code by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)))
            .background(MaterialTheme.colorScheme.tertiary),
    ) {
        TextField(
            value = code,
            onValueChange = { value ->
                code = value
            },
            singleLine = true,
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
            placeholder = {
                CustomizedText(
                    text = stringResource(id = R.string.promo_hint),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = dimensionResource(id = R.dimen.text_size_medium)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            trailingIcon = {
                CustomizedText(
                    text = stringResource(id = R.string.apply),
                    fontSize = dimensionResource(id = R.dimen.text_size_medium),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                        .clickable { onApplyClick.invoke(code) }
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.discount_icon),
                    contentDescription = "Phone",
                    tint = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
                )
            },
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onTertiary
            ),
            modifier = Modifier.fillMaxWidth()
        )
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
            {}
        )
    }
}