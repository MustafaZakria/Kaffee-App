package com.example.kaffeeapp.presentation.main.cart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.entities.DeliveryType
import com.example.kaffeeapp.presentation.main.cart.models.OrderUi
import com.example.kaffeeapp.presentation.main.cart.models.InputValidationResult
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun BeforeOrderList(
    orderUi: OrderUi,
    errorHandler: InputValidationResult,
    navigateToMapScreen: () -> Unit,
    onPhoneValueChange: (String) -> Unit,
    onAddNoteClick: () -> Unit,
    onSelectBranchClick: () -> Unit
) {
    //delivery content
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomizedText(
            text = if (orderUi.isDeliveryEnabled) stringResource(id = R.string.home_address) else stringResource(
                id = R.string.pick_up_branch
            ),
            fontSize = dimensionResource(id = R.dimen.text_size_16),
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))

        if (orderUi.isDeliveryEnabled) {
            var address = ""
            if (orderUi.deliveryValue != null) {
                address =
                    (orderUi.deliveryValue as? DeliveryType.HomeDelivery)?.address ?: ""
            }
            DeliverySection(
                address = address,
                onEditAddressClick = {
                    navigateToMapScreen.invoke()
                },
                addressErrorValue = errorHandler.addressErrorValue,
                onAddNoteClick = { onAddNoteClick.invoke() }
            )
        } else {
            var branchName = ""
            if (orderUi.deliveryValue != null) {
                branchName =
                    (orderUi.deliveryValue as? DeliveryType.BranchDelivery)?.branchName
                        ?: ""
            }
            PickUpSection(
                branch = branchName,
                addressErrorValue = errorHandler.addressErrorValue,
                onClick = { onSelectBranchClick.invoke() }
            )
        }

        //telephone number section
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        PhoneNumberContainer(
            onPhoneValueChange = { value ->
                onPhoneValueChange.invoke(value)
            },
            phoneErrorValue = errorHandler.phoneErrorValue,
            phoneNumber = orderUi.phoneNumberValue
        )
    }
}