package com.example.kaffeeapp.presentation.main.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

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
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_phone),
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
        if (phoneErrorValue.isNotBlank()) {
            CustomizedText(
                text = phoneErrorValue,
                fontSize = dimensionResource(id = R.dimen.text_size_small),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }

}