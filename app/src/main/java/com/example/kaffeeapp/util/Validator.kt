package com.example.kaffeeapp.util

import com.example.kaffeeapp.data.entities.DeliveryType
import com.example.kaffeeapp.util.Constants.INVALID_VALUE
import com.example.kaffeeapp.util.Constants.NULL_VALUE
import com.example.kaffeeapp.util.Constants.PROMO_EXPIRED_VALUE

object Validator {

    fun validatePhoneNumber(value: String): String {
        if(value.isBlank()) {
            return NULL_VALUE
        } else if (value.length < 10) {
            return INVALID_VALUE
        }
        return ""
    }

    fun validateDeliveryDetail(value: DeliveryType?): String {
        if(value == null) {
            return NULL_VALUE
        }
        return ""
    }

    fun validatePromoCode(result: String?): String {
        if(result == null) {
            return PROMO_EXPIRED_VALUE
        }
        return ""
    }
}