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
import com.example.kaffeeapp.presentation.main.cart.models.CartDetails
import com.example.kaffeeapp.presentation.main.cart.models.CartInputsHandler
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun BeforeOrderList(
    cartDetails: CartDetails,
    errorHandler: CartInputsHandler,
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
                onAddNoteClick = { onAddNoteClick.invoke() }
            )
        } else {
            var branchName = ""
            if (cartDetails.deliveryValue != null) {
                branchName =
                    (cartDetails.deliveryValue as? DeliveryType.BranchDelivery)?.branchName
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
            phoneNumber = cartDetails.phoneNumberValue
        )
    }
}