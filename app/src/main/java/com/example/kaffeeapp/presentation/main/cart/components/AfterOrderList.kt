package com.example.kaffeeapp.presentation.main.cart.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.cart.models.InputValidationResult
import com.example.kaffeeapp.presentation.main.cart.models.OrderUi

@Composable
fun AfterOrderList(
    orderUi: OrderUi,
    errorHandler: InputValidationResult,
    isDeliveryEnabled: Boolean,
    onApplyPromoClick: (String) -> Unit,
) {
    //promo code
    PromoCodeContainer(
        onApplyClick = { code -> onApplyPromoClick.invoke(code) },
        promoErrorValue = errorHandler.promoErrorValue
    )
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))

    //payment summary
    PaymentSummarySection(
        itemsPrice = orderUi.itemsPrice,
        discountValue = orderUi.discountValue,
        deliveryFee = orderUi.deliveryFee,
        totalPrice = orderUi.getTotalCost(),
        isDeliveryEnabled = isDeliveryEnabled
    )
}