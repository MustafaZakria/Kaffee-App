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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.kaffeeapp.components.ImageLoaderWithUrl
import com.example.kaffeeapp.components.TopBarTitle
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.presentation.main.drinkDetails.DrinkDetailsViewModel
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.ui.theme.lightBrown
import com.example.kaffeeapp.ui.theme.lightWhite

@Composable
fun CartScreen(
    viewModel: DrinkDetailsViewModel
) {
    val drinkOrders = viewModel.drinkOrders
    val itemsPrice = viewModel.itemsPrice.value
    val discountValue = viewModel.discountValue.value
    val deliveryFee = viewModel.deliveryFee.value
    val totalPrice = viewModel.totalPrice.toString()

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
        itemsPrice = itemsPrice,
        discountValue = discountValue,
        deliveryFee = deliveryFee,
        totalPrice = totalPrice
    )
}

@Composable
fun CartScreenContent(
    orders: List<DrinkOrder>,
    onDeleteOrderClick: (Int) -> Unit,
    onChangeQuantityClick: (Int, Int) -> Unit,
    onApplyPromoClick: (String) -> Unit,
    itemsPrice: String,
    discountValue: String,
    deliveryFee: String,
    totalPrice: String
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopBarTitle(title = stringResource(id = R.string.cart))
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = dimensionResource(id = R.dimen.padding_bottom_navigation),
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium)
                )
        ) {
            var isDeliveryEnabled by rememberSaveable { mutableStateOf(true) }

            if (orders.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    //selecting pick up way
                    SelectDeliverWay(
                        modifier = Modifier
                            .fillMaxWidth(),
                        isDeliveryEnabled = isDeliveryEnabled,
                        onDeliveryClick = {
                            isDeliveryEnabled = true
                        },
                        onPickUpClick = {
                            isDeliveryEnabled = false
                        }
                    )
                    //drink orders
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            BeforeOrderList(isDeliveryEnabled = isDeliveryEnabled)
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
                            AfterOrderList(
                                itemsPrice = itemsPrice,
                                discountValue = discountValue,
                                deliveryFee = deliveryFee,
                                totalPrice = totalPrice,
                                isDeliveryEnabled = isDeliveryEnabled,
                                onApplyPromoClick = { code -> onApplyPromoClick.invoke(code) }
                            )
                        }
                    }
                }
            } else {
                EmptyList(message = stringResource(id = R.string.no_orders))
            }
        }
    }
}

@Composable
fun BeforeOrderList(
    isDeliveryEnabled: Boolean
) {
    //spacer
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
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
            DeliverySection(
                address = "",
                onEditAddressClick = {},
                onAddNoteClick = {}
            )
        } else {
            PickUpSection(
                branch = stringResource(id = R.string.pick_up_nearby_branch),
                onClick = {}
            )
        }

        //telephone number section
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        PhoneNumberContainer(
            onPhoneValueChange = {}
        )
    }
    //spacer
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
    HorizontalDivider(
        color = MaterialTheme.colorScheme.secondary,
    )
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
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
    //spacer
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
    HorizontalDivider(
        color = MaterialTheme.colorScheme.secondary,
    )
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
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
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))

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
            if(isDeliveryEnabled) {
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
fun PickUpSection(
    branch: String,
    onClick: () -> Unit
) {
    RoundedButtonWithIcon(
        backgroundColor = MaterialTheme.colorScheme.tertiary,
        borderStroke = BorderStroke(1.dp, MaterialTheme.colorScheme.onTertiary),
        leadingIconId = R.drawable.edit_icon,
        leadingIconDesc = "edit",
        trailingIconId = R.drawable.arrow_right_icon,
        trailingIconDesc = "arrow right",
        color = MaterialTheme.colorScheme.onTertiary,
        text = branch
    ) {
        onClick.invoke()
    }
}

@Composable
fun DeliverySection(
    address: String,
    onEditAddressClick: () -> Unit,
    onAddNoteClick: () -> Unit
) {
//    CustomizedText(
//        text = address,
//        fontSize = dimensionResource(id = R.dimen.text_size_medium),
//        color = MaterialTheme.colorScheme.onSecondary,
//        fontWeight = FontWeight.Medium
//    )
//    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_x_small)))
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RoundedButtonWithIcon(
            modifier = Modifier.weight(1f),
            backgroundColor = MaterialTheme.colorScheme.tertiary,
            leadingIconId = R.drawable.edit_icon,
            leadingIconDesc = "edit",
            trailingIconId = R.drawable.arrow_right_icon,
            trailingIconDesc = "arrow right",
            color = MaterialTheme.colorScheme.onTertiary,
            text = if (address == "") stringResource(id = R.string.edit_address) else address,
            borderStroke = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.onTertiary
            )
        ) {
            onEditAddressClick.invoke()
        }
        RoundedButtonWithIcon(
            backgroundColor = MaterialTheme.colorScheme.tertiary,
            leadingIconId = R.drawable.document_icon,
            leadingIconDesc = "add",
            color = MaterialTheme.colorScheme.onTertiary,
            text = stringResource(id = R.string.add_note),
            borderStroke = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.onTertiary
            )
        ) {
            onAddNoteClick.invoke()
        }
    }
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
                        .padding(vertical = dimensionResource(id = R.dimen.padding_x_small))
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
                        .padding(vertical = dimensionResource(id = R.dimen.padding_x_small)),
                    color = if (!isDeliveryEnabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
                )
            }
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

            QuantityAndClosePart(
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
fun PhoneNumberContainer(
    onPhoneValueChange: (String) -> Unit
) {
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }

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
                phoneNumber = value
                errorMessage = if (phoneNumber == "") {
                    "required"
                } else if (phoneNumber.length < 10) {
                    "Enter valid phone number"
                } else {
                    ""
                }
            },
            isError = errorMessage != "",
//            supportingText = {
//                if (errorMessage.isNotBlank()) {
//                    CustomizedText(
//                        text = errorMessage,
//                        color = MaterialTheme.colorScheme.error,
//                        modifier = Modifier.testTag(errorMessage),
//                        fontSize = dimensionResource(id = R.dimen.text_size_small)
//                    )
//                }
//            },
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
fun QuantityAndClosePart(
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
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.lightBrown)
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
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.lightBrown)
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
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large)),
        colors = CardDefaults.cardColors().copy(
            containerColor = backgroundColor
        ),
        border = borderStroke
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_small),
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
                    tint = MaterialTheme.colorScheme.onTertiary,
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
            "0.0",
            "0.0",
            "0.0",
            "0.0"
        )
    }
}