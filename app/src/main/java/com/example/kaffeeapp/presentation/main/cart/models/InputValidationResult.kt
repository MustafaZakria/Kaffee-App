package com.example.kaffeeapp.presentation.main.cart.models

data class InputValidationResult(
    val phoneErrorValue: String = "",
    val addressErrorValue: String = "",
    val promoErrorValue: String = "",
) {
    fun isValuesValid(): Boolean {
        return phoneErrorValue.isBlank() &&
                addressErrorValue.isBlank() &&
                addressErrorValue.isBlank()
    }
}
