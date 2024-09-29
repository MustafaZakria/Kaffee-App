package com.example.kaffeeapp.presentation.main.cart

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.kaffeeapp.components.ImageLoaderWithUrl
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.components.TopBarTitle
import com.example.kaffeeapp.data.entities.DeliveryMethod
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.util.Constants.ADDRESS_ADDED_SUCCESSFULLY
import com.example.kaffeeapp.util.Constants.NETWORK_ERROR
import com.example.kaffeeapp.util.Constants.ORDER_SUCCESS
import com.example.kaffeeapp.util.model.OrderCost
import com.example.kaffeeapp.util.model.Resource

@Composable
fun CartScreen(
    viewModel: CartViewModel,
    navigateToMapScreen: () -> Unit
) {
    val drinkOrders = viewModel.drinkOrders
    val orderCost = viewModel.orderCost
    val totalPrice = viewModel.totalPrice.toString()
    val deliveryMethod = viewModel.deliveryMethod
    val isDeliveryEnabled = viewModel.isDeliveryEnabled
    val isPhoneNumberValid = viewModel.isPhoneNumberValid
    val phoneNumberState = viewModel.phoneNumberState
    val isAddressNull = viewModel.isAddressNull
    val orderResult = viewModel.orderResult

    if (drinkOrders.isEmpty()) {
        viewModel.removeBadgeOnCart()
    }

    CartScreenContent(
        orders = drinkOrders,
        onDeleteOrderClick = { index -> viewModel.removeDrinkFromCart(index) },
        onChangeQuantityClick = { index, newValue ->
            viewModel.setDrinkOrderQuantity(
                index,
                newValue
            )
        },
        onApplyPromoClick = { code -> viewModel.setPromoCode(code) },
        orderCost = orderCost.value,
        deliveryMethod = deliveryMethod.value,
        isDeliveryEnabled = isDeliveryEnabled.value,
        onDeliveryEnableChange = { isEnabled -> viewModel.setDeliveryEnabledValue(isEnabled) },
        isAddressNull = isAddressNull.value,
        navigateToMapScreen = { navigateToMapScreen.invoke() },
        onPhoneValueChange = { value -> viewModel.onPhoneNumberValueChange(value) },
        isPhoneNumberValid = isPhoneNumberValid.value,
        phoneNumber = phoneNumberState.value,
        totalPrice = totalPrice,
        orderResult = orderResult,
        onOrderClick = { viewModel.submitOrder() },
        resetOrderState = { viewModel.resetOrderResponseState() }
    )
}

@Composable
fun CartScreenContent(
    orders: List<DrinkOrder>,
    onDeleteOrderClick: (Int) -> Unit,
    onChangeQuantityClick: (Int, Int) -> Unit,
    onApplyPromoClick: (String) -> Unit,
    orderCost: OrderCost,
    deliveryMethod: DeliveryMethod?,
    isDeliveryEnabled: Boolean,
    onDeliveryEnableChange: (Boolean) -> Unit,
    isAddressNull: Boolean?,
    navigateToMapScreen: () -> Unit,
    onPhoneValueChange: (String) -> Unit,
    isPhoneNumberValid: Boolean?,
    phoneNumber: String,
    totalPrice: String,
    orderResult: Resource<Boolean>?,
    onOrderClick: () -> Unit,
    resetOrderState: () -> Unit
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
            if (orders.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    //selecting pick up way
                    SelectDeliverWay(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_medium),
                                end = dimensionResource(id = R.dimen.padding_medium)
                            ),
                        isDeliveryEnabled = isDeliveryEnabled,
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
                                isDeliveryEnabled = isDeliveryEnabled,
                                deliveryMethod = deliveryMethod,
                                navigateToMapScreen = { navigateToMapScreen.invoke() },
                                onPhoneValueChange = { value -> onPhoneValueChange.invoke(value) },
                                isAddressNull = isAddressNull,
                                isPhoneNumberValid = isPhoneNumberValid,
                                phoneNumber = phoneNumber
                            )
                            //spacer
                            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.secondary,
                            )
                            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_x_small)))
                        }
                        itemsIndexed(orders) { index, order ->
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
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.secondary,
                            )
                            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))

                            AfterOrderList(
                                itemsPrice = orderCost.itemsPrice,
                                discountValue = orderCost.discountValue,
                                deliveryFee = orderCost.deliveryFee,
                                totalPrice = totalPrice,
                                isDeliveryEnabled = isDeliveryEnabled,
                                onApplyPromoClick = { code -> onApplyPromoClick.invoke(code) }
                            )
                            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
                        }
                    }
                    OrderBox(
                        totalPrice = totalPrice,
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
        orderResult = orderResult,
        context = LocalContext.current,
        isAddressNull = isAddressNull,
        resetOrderState = { resetOrderState.invoke() }
    )
}

@Composable
fun OnResultState(
    orderResult: Resource<Boolean>?,
    context: Context,
    isAddressNull: Boolean?,
    resetOrderState: () -> Unit
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
    LaunchedEffect(key1 = orderResult) {
        when (orderResult) {
            is Resource.Success -> {
                Toast.makeText(context, ORDER_SUCCESS, Toast.LENGTH_SHORT).show()
                resetOrderState.invoke()
            }
            is Resource.Failure -> {
                Toast.makeText(context, NETWORK_ERROR, Toast.LENGTH_SHORT).show()
                resetOrderState.invoke()
            }
            else -> {}
        }
    }
    LaunchedEffect(key1 = isAddressNull) {
        if(isAddressNull?.not() == true) {
            Toast.makeText(context, ADDRESS_ADDED_SUCCESSFULLY, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun OrderBox(
    totalPrice: String,
    onOrderClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
                vertical = dimensionResource(id = R.dimen.padding_medium)
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
            ) {
                CustomizedText(
                    text = stringResource(id = R.string.cash),
                    fontSize = dimensionResource(id = R.dimen.text_size_medium),
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontWeight = FontWeight.Medium
                )
                CustomizedText(
                    text = stringResource(id = R.string.drink_price, totalPrice),
                    fontSize = dimensionResource(id = R.dimen.text_size_18),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)))
            Button(
                onClick = { onOrderClick.invoke() },
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
                modifier = Modifier.weight(1f)
            ) {
                CustomizedText(
                    text = stringResource(id = R.string.order),
                    fontSize = dimensionResource(id = R.dimen.text_size_18),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}

@Composable
fun BeforeOrderList(
    isDeliveryEnabled: Boolean,
    deliveryMethod: DeliveryMethod?,
    navigateToMapScreen: () -> Unit,
    onPhoneValueChange: (String) -> Unit,
    isAddressNull: Boolean?,
    isPhoneNumberValid: Boolean?,
    phoneNumber: String
) {
    //delivery content
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomizedText(
            text = if (isDeliveryEnabled) stringResource(id = R.string.delivery_address) else stringResource(
                id = R.string.pick_up_branch
            ),
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))

        if (isDeliveryEnabled) {
            var address = ""
            if (deliveryMethod != null) {
                address = (deliveryMethod as? DeliveryMethod.AddressDelivery)?.address ?: ""
            }
            DeliverySection(
                address = address,
                onEditAddressClick = {
                    navigateToMapScreen.invoke()
                },
                isAddressNull = isAddressNull,
                onAddNoteClick = {}
            )
        } else {
            var branchName = ""
            if (deliveryMethod != null) {
                branchName = (deliveryMethod as? DeliveryMethod.BranchDelivery)?.branchAddress ?: ""
            }
            PickUpSection(
                branch = branchName,
                isAddressNull = isAddressNull,
                onClick = {}
            )
        }

        //telephone number section
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        PhoneNumberContainer(
            onPhoneValueChange = { value ->
                onPhoneValueChange.invoke(value)
            },
            isNumberValid = isPhoneNumberValid,
            phoneNumber = phoneNumber
        )
    }
}

@Composable
fun AfterOrderList(
    itemsPrice: String,
    discountValue: String,
    deliveryFee: String,
    totalPrice: String,
    isDeliveryEnabled: Boolean,
    onApplyPromoClick: (String) -> Unit,
) {
    //promo code
    PromoCodeContainer(
        onApplyClick = { code -> onApplyPromoClick.invoke(code) }
    )
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))

    //payment summary
    PaymentSummary(
        itemsPrice = itemsPrice,
        discountValue = discountValue,
        deliveryFee = deliveryFee,
        totalPrice = totalPrice,
        isDeliveryEnabled = isDeliveryEnabled
    )
}

@Composable
fun SelectDeliverWay(
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
    isAddressNull: Boolean?,
    onClick: () -> Unit
) {
    RoundedButtonWithIcon(
        backgroundColor = MaterialTheme.colorScheme.tertiary,
        borderStroke = BorderStroke(
            1.dp,
            if (isAddressNull == true) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
        ),
        leadingIconId = R.drawable.edit_icon,
        leadingIconDesc = "edit",
        trailingIconId = R.drawable.arrow_right_icon,
        trailingIconDesc = "arrow right",
        color = MaterialTheme.colorScheme.primary,
        text = if (branch == "") stringResource(id = R.string.pick_up_nearby_branch) else branch
    ) {
        onClick.invoke()
    }
}

@Composable
fun DeliverySection(
    address: String,
    isAddressNull: Boolean?,
    onEditAddressClick: () -> Unit,
    onAddNoteClick: () -> Unit
) {
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
                if (isAddressNull == true) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
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
}


@Composable
fun OrderCard(
    order: DrinkOrder,
    orderPrice: String,
    onDeleteClick: () -> Unit,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.tertiary
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
                        .size(85.dp)
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_6))),
                    imageUrl = order.imageUrl
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.Top)
                    .weight(1f)
                    .height(85.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CustomizedText(
                    text = order.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.headlineSmall,
                    textLines = 1,
                )
                CustomizedText(
                    text = order.size,
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    color = MaterialTheme.colorScheme.onBackground
                )
                CustomizedText(
                    text = stringResource(id = R.string.drink_price, orderPrice),
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.headlineSmall,
                    textLines = 1,
                )
            }

            QuantityAndCloseColumn(
                modifier = Modifier
                    .height(85.dp)
                    .weight(1f),
                quantity = order.quantity,
                onMinusClick = { onMinusClick.invoke() },
                onDeleteClick = { onDeleteClick.invoke() },
                onPlusClick = { onPlusClick.invoke() }
            )
        }
    }
}


@Composable
fun PhoneNumberContainer(
    onPhoneValueChange: (String) -> Unit,
    isNumberValid: Boolean?,
    phoneNumber: String
) {
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
            isError = (isNumberValid?.not() == true),
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
fun PaymentSummary(
    itemsPrice: String,
    discountValue: String,
    deliveryFee: String,
    totalPrice: String,
    isDeliveryEnabled: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small))
        ) {
            //items
            RowItemPrice(
                item = stringResource(id = R.string.items),
                price = stringResource(id = R.string.price_value, itemsPrice)
            )
            //discount
            RowItemPrice(
                item = stringResource(id = R.string.discount),
                price = stringResource(id = R.string.discount_price, discountValue)
            )
            if (isDeliveryEnabled) {
                //delivery
                RowItemPrice(
                    item = stringResource(id = R.string.delivery),
                    price = if (deliveryFee.toDouble() == 0.0) stringResource(id = R.string.free) else stringResource(
                        id = R.string.price_value,
                        deliveryFee
                    )
                )
            }
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_x_small)))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_x_small)))
            //total
            RowItemPrice(
                item = stringResource(id = R.string.total),
                price = stringResource(id = R.string.drink_price, totalPrice),
                fontWeightItem = FontWeight.Medium
            )
        }
    }
}

@Composable
fun QuantityAndCloseColumn(
    modifier: Modifier,
    quantity: Int,
    onDeleteClick: () -> Unit,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    onDeleteClick.invoke()
                },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.close_icon),
                contentDescription = "delete",
                tint = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.size(24.dp)
            )
        }
        //drink quantity
        Row(
            modifier = Modifier
                .align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(dimensionResource(id = R.dimen.circle_size_32))
                    .padding(dimensionResource(id = R.dimen.padding_x_small))
                    .clickable {
                        onMinusClick.invoke()
                    },
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                shape = CircleShape,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides
                            if (quantity == 1) MaterialTheme.colorScheme.onTertiary.copy(
                                alpha = 0.2f
                            )
                            else MaterialTheme.colorScheme.onTertiary.copy(alpha = 1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.negative_icon),
                        contentDescription = "minus",
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.padding_x_small))
                    )
                }
            }
            CustomizedText(
                text = quantity.toString(),
                fontSize = dimensionResource(id = R.dimen.text_size_medium),
                color = MaterialTheme.colorScheme.onTertiary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
            Card(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(dimensionResource(id = R.dimen.circle_size_32))
                    .padding(dimensionResource(id = R.dimen.padding_x_small))
                    .clickable {
                        onPlusClick.invoke()
                    },
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                shape = CircleShape,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.positive_icon),
                    contentDescription = "minus",
                    tint = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_x_small))
                )
            }
        }

    }
}

@Composable
fun RowItemPrice(
    item: String,
    fontWeightItem: FontWeight = FontWeight.Normal,
    price: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomizedText(
            text = item,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = fontWeightItem
        )
        CustomizedText(
            text = price,
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onTertiary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun RoundedButtonWithIcon(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    borderStroke: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    leadingIconId: Int,
    leadingIconDesc: String,
    trailingIconId: Int? = null,
    trailingIconDesc: String = "",
    color: Color,
    text: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick.invoke() },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
        colors = CardDefaults.cardColors().copy(
            containerColor = backgroundColor
        ),
        border = borderStroke
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_10),
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
        ) {
            Icon(
                painter = painterResource(id = leadingIconId),
                contentDescription = leadingIconDesc,
                tint = color,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
            CustomizedText(
                text = text,
                fontSize = dimensionResource(id = R.dimen.text_size_medium),
                fontWeight = FontWeight.Medium,
                color = color,
                modifier = Modifier
            )
            if (trailingIconId != null) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = trailingIconId),
                    contentDescription = trailingIconDesc,
                    tint = color,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CartScreenPreview() {
    val orders = listOf(
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
    KaffeeAppTheme {
        CartScreenContent(
            orders,
            {},
            { _, _ -> },
            {},
            OrderCost(),
            null,
            true,
            {},
            false,
            {},
            {},
            true,
            "",
            "",
            Resource.Loading(),
            {},
            {}
        )
    }
}