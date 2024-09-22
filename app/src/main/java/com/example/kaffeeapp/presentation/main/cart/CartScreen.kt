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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.TopBarTitle
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.presentation.main.drinkDetails.DrinkDetailsViewModel
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme

@Composable
fun CartScreen(
    viewModel: DrinkDetailsViewModel
) {
    val drinkOrders = viewModel.drinkOrders

    CartScreenContent(
        orders = drinkOrders
    )
}

@Composable
fun CartScreenContent(
    orders: List<DrinkOrder>
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
                    bottom = 64.dp,
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium)
                )
        ) {
            var isDeliveryEnabled by rememberSaveable { mutableStateOf(false) }

            if(orders.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    //selecting pick up way
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_12))

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
                                    .clickable { isDeliveryEnabled = true }
                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_x_small)))
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
                                    .clickable { isDeliveryEnabled = false }
                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_x_small)))
                                    .background(if (!isDeliveryEnabled) MaterialTheme.colorScheme.primary else Color.Transparent)
                                    .weight(1f)
                            ) {
                                CustomizedText(
                                    text = stringResource(id = R.string.delivery),
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
                            CustomizedText(
                                text = stringResource(id = R.string.add_your_address),
                                fontSize = dimensionResource(id = R.dimen.text_size_small),
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_x_small)))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                RoundedButtonWithIcon(
                                    backgroundColor = MaterialTheme.colorScheme.tertiary,
                                    iconId = R.drawable.edit_icon,
                                    iconDesc = "edit",
                                    color = MaterialTheme.colorScheme.onTertiary,
                                    text = stringResource(id = R.string.edit_address),
                                    textModifier = Modifier,
                                    borderStroke = BorderStroke(
                                        1.dp,
                                        MaterialTheme.colorScheme.onTertiary
                                    )
                                ) {

                                }
                                RoundedButtonWithIcon(
                                    backgroundColor = MaterialTheme.colorScheme.tertiary,
                                    iconId = R.drawable.document_icon,
                                    iconDesc = "add",
                                    color = MaterialTheme.colorScheme.onTertiary,
                                    text = stringResource(id = R.string.add_note),
                                    textModifier = Modifier,
                                    borderStroke = BorderStroke(
                                        1.dp,
                                        MaterialTheme.colorScheme.onTertiary
                                    )
                                ) {

                                }
                            }
                        } else {
                            Card(
                                modifier = Modifier.clickable {

                                },
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large)),
                                colors = CardDefaults.cardColors().copy(
                                    containerColor = MaterialTheme.colorScheme.tertiary
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onTertiary)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        dimensionResource(
                                            id = R.dimen.padding_small
                                        )
                                    ),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(
                                        vertical = dimensionResource(id = R.dimen.padding_small),
                                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.edit_icon),
                                        contentDescription = "edit",
                                        tint = MaterialTheme.colorScheme.onTertiary
                                    )
                                    CustomizedText(
                                        text = stringResource(id = R.string.pick_up_nearby_branch),
                                        fontSize = dimensionResource(id = R.dimen.text_size_small),
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onTertiary,
                                        modifier = Modifier
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(
                                        painter = painterResource(id = R.drawable.arrow_right_icon),
                                        contentDescription = "arrow right",
                                        tint = MaterialTheme.colorScheme.onTertiary
                                    )
                                }
                            }
                        }
                    }
                    //spacer
                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
                    //drink orders
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(orders) { order ->

                        }
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.empty_icon),
                        contentDescription = stringResource(id = R.string.error_img_desc),
                        tint = Color.Black
                    )
                    CustomizedText(
                        text = stringResource(id = R.string.no_orders),
                        fontSize = dimensionResource(id = R.dimen.text_size_medium),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                    )
                }
            }
        }
    }
}

@Composable
fun RoundedButtonWithIcon(
    backgroundColor: Color,
    borderStroke: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    iconId: Int,
    iconDesc: String,
    color: Color,
    text: String,
    textModifier: Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable { onClick.invoke() },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_large)),
        colors = CardDefaults.cardColors().copy(
            containerColor = backgroundColor
        ),
        border = borderStroke
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_x_small)),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                vertical = dimensionResource(id = R.dimen.padding_x_small),
                horizontal = dimensionResource(id = R.dimen.padding_small)
            )
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = iconDesc,
                tint = color
            )
            CustomizedText(
                text = text,
                fontSize = dimensionResource(id = R.dimen.text_size_small),
                fontWeight = FontWeight.Medium,
                color = color,
                modifier = textModifier
            )
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
            orders
        )
    }
}