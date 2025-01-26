package com.example.kaffeeapp.presentation.main.cart.models

import com.example.kaffeeapp.data.entities.BranchDetails

sealed class CartScreenAction {
    data class OrderDeleted(val index: Int) : CartScreenAction()
    data class QuantityChanged(val index: Int, val newQuantity: Int) : CartScreenAction()
    data class PromoCodeApplied(val code: String) : CartScreenAction()
    data object OrderSubmitted : CartScreenAction()
    data class BranchSelected(val branch: BranchDetails) : CartScreenAction()
    data class PhoneChanged(val phone: String) : CartScreenAction()
    data class NoteSaved(val note: String) : CartScreenAction()
    data class DeliveryEnabledChanged(val isEnabled: Boolean) : CartScreenAction()
}